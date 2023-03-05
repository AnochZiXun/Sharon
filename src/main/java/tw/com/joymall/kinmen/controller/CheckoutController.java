package tw.com.joymall.kinmen.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.com.joymall.kinmen.Utils;
import tw.com.joymall.kinmen.entity.Packet;
import tw.com.joymall.kinmen.entity.MerchandiseImage;
import tw.com.joymall.kinmen.entity.Cart;
import tw.com.joymall.kinmen.entity.Staff;
import tw.com.joymall.kinmen.entity.Merchandise;
import tw.com.joymall.kinmen.entity.MerchandiseSpecification;
import tw.com.joymall.kinmen.entity.ConceptTechHistory;
import tw.com.joymall.kinmen.repository.CartRepository;
import tw.com.joymall.kinmen.repository.RegularRepository;
import tw.com.joymall.kinmen.repository.MerchandiseSpecificationRepository;
import tw.com.joymall.kinmen.repository.PacketRepository;
import tw.com.joymall.kinmen.repository.MerchandiseImageRepository;
import tw.com.joymall.kinmen.repository.StaffRepository;
import tw.com.joymall.kinmen.repository.MerchandiseRepository;
import tw.com.joymall.kinmen.repository.ConceptTechHistoryRepository;
import tw.com.joymall.kinmen.service.Services;

/**
 * 結帳付款
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/checkOut")
public class CheckoutController {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ConceptTechHistoryRepository conceptTechHistoryRepository;

	@Autowired
	private MerchandiseRepository merchandiseRepository;

	@Autowired
	private MerchandiseImageRepository merchandiseImageRepository;

	@Autowired
	private MerchandiseSpecificationRepository merchandiseSpecificationRepository;

	@Autowired
	private PacketRepository packetRepository;

	@Autowired
	private RegularRepository regularRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private Services services;

	/**
	 * 列表(結帳付款)
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return 網頁
	 */
	@RequestMapping(value = "/{boothId:\\d+}/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@SuppressWarnings("null")
	private ModelAndView welcome(@PathVariable Integer boothId, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParserConfigurationException {
		if (request.getRemoteUser() != null) {
			return new ModelAndView("redirect:/");
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "requestURI", request.getRequestURI());
		Integer me = (Integer) session.getAttribute("me");
		if (me != null) {
			documentElement.setAttribute("me", me.toString());
		}
		services.buildFooterElement(documentElement);//底部

		ArrayList<JSONObject> arrayList = (ArrayList<JSONObject>) session.getAttribute("cart");
		if (arrayList == null || arrayList.isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("checkOut");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		if (arrayList != null && !arrayList.isEmpty()) {
			int size = arrayList.size();
			documentElement.setAttribute("cart", size > 9 ? "9+" : Integer.toString(size));
		}

		Integer totalAmount = 0;
		StringBuilder stringBuilder = new StringBuilder();
		Element elementItems = Utils.createElement("items", documentElement);
		for (JSONObject itemInCart : arrayList) {
			if (itemInCart.getInt("booth") == boothId) {
				Long merchandiseId = itemInCart.getLong("merchandise");
				Merchandise merchandise = merchandiseRepository.findOne(merchandiseId);
				MerchandiseImage merchandiseImage = merchandiseImageRepository.findTopByMerchandiseOrderByOrdinal(merchandise);
				MerchandiseSpecification merchandiseSpecification = null;
				if (itemInCart.has("specification")) {
					merchandiseSpecification = merchandiseSpecificationRepository.findOne(itemInCart.getLong("specification"));
				}
				int price = merchandise.getPrice(), quantity = itemInCart.getInt("quantity"), subTotal = price * quantity;

				Element elementItem = Utils.createElement("item", elementItems);
				elementItem.setAttribute("id", merchandiseId.toString());
				elementItem.setTextContent(merchandise.getName());
				if (merchandiseImage != null) {
					elementItem.setAttribute("imageId", merchandiseImage.getId().toString());
				}
				if (merchandiseSpecification != null) {
					elementItem.setAttribute("specificationName", merchandiseSpecification.getName());
				}
				elementItem.setAttribute("price", Integer.toString(price));
				elementItem.setAttribute("quantity", Integer.toString(quantity));
				elementItem.setAttribute("subTotal", Integer.toString(subTotal));

				if (stringBuilder.length() > 0) {
					stringBuilder.append("#").append(merchandiseId);
				} else {
					stringBuilder.append(merchandiseId);
				}
				totalAmount += subTotal;
			}
		}

		Staff booth = staffRepository.findOne(boothId);
		String boothName = booth.getName(), merchantID = booth.getMerchantId();
		Element elementStore = Utils.createElement("store", documentElement);
		elementStore.setAttribute("id", boothId.toString());
		elementStore.setAttribute("MerchantID", merchantID == null ? "" : merchantID);
		elementStore.setAttribute("totalAmount", totalAmount.toString());
		elementStore.setAttribute("tradeDesc", "跨境電子商務：".concat(boothName));
		elementStore.setAttribute("itemName", stringBuilder.toString());
		elementStore.setTextContent(boothName);

		ModelAndView modelAndView = new ModelAndView("checkOut");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 觸發支付流程
	 *
	 * @param session
	 * @return JSONObject
	 */
	@RequestMapping(value = "/{boothId:\\d+}/", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings({"Convert2Diamond", "UnusedAssignment"})
	private String checkingOut(@PathVariable Integer boothId, HttpSession session) throws URISyntaxException {
		JSONObject jsonObject = new JSONObject();

		Integer me = (Integer) session.getAttribute("me");
		if (me == null) {
			return jsonObject.put("reason", "請先登入！").put("redirect", "/logIn.asp").put("response", false).toString();
		}

		ArrayList<JSONObject> arrayListCart = (ArrayList<JSONObject>) session.getAttribute("cart");
		if (arrayListCart == null || arrayListCart.isEmpty()) {
			return jsonObject.put("reason", "購物車是空的或您的登入週期已經逾時！").put("redirect", "/cart/").put("response", false).toString();
		}

		Staff booth = staffRepository.findOne(boothId);
		HashMap<String, Object> request = new HashMap<String, Object>();

		/*
		 特店代號
		 */
		String merchantId = booth.getMerchantId(), merchantKey = booth.getMerchantKey();
		if ((merchantId == null || merchantId.isEmpty()) || (merchantKey == null || merchantKey.isEmpty())) {
			return jsonObject.put("reason", "該店家尚未申請 iCarry！").put("redirect", "/cart/").put("response", false).toString();
		}
		request.put("MerchantID", merchantId);

		GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("Asia/Taipei"), Locale.TAIWAN);
		Long timeInMillis = gregorianCalendar.getTimeInMillis();
		Date time = gregorianCalendar.getTime();

		/*
		 特店訂單編號
		 */
		String orderNo = timeInMillis.toString();
		Integer suffix = 0;
		while (packetRepository.countByOrderNo(orderNo) > 0) {
			suffix++;
		}
		orderNo += suffix.toString();
		request.put("OrderNo", orderNo);

		/*
		 訂單名稱
		 */
		request.put("OrderName", booth.getName());//不一定得寫入資料庫但必須送出請求

		/*
		 訂單說明
		 */
		request.put("OrderComment", new SimpleDateFormat("yyyy-MM-dd hh:mm:ssaa", Locale.TAIWAN).format(time));//不一定得寫入資料庫但必須送出請求

		/*
		 商品
		 */
		Packet packet = new Packet(regularRepository.findOne(me), booth, orderNo, time);
		packetRepository.saveAndFlush(packet);
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();//商品陣列
		Iterator<JSONObject> iterator = arrayListCart.iterator();
		while (iterator.hasNext()) {
			JSONObject itemInCart = iterator.next();

			Cart cart;
			if (Objects.equals(boothId, itemInCart.getInt("booth"))) {
				Merchandise merchandise = merchandiseRepository.findOne(itemInCart.getLong("merchandise"));
				int quantity = itemInCart.getInt("quantity"), price = merchandise.getPrice();

				if (itemInCart.has("specification")) {
					MerchandiseSpecification merchandiseSpecification = merchandiseSpecificationRepository.findOne(itemInCart.getLong("specification"));

					cart = new Cart(merchandise, merchandiseSpecification.getName(), Integer.valueOf(quantity).shortValue(), price, packet);
				} else {
					cart = new Cart(merchandise, null, Integer.valueOf(quantity).shortValue(), price, packet);
				}
				cartRepository.saveAndFlush(cart);

				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("ItemName", merchandise.getName());//商品名稱
				item.put("ItemAmount", quantity);//商品數量
				item.put("ItemPrice", merchandise.getPrice());//商品單價
				item.put("ItemNO", merchandise.getId().toString());//特店商品編號(不可為null)
				if (merchandise.getItemSId() == null) {
					item.put("ItemWeight", merchandise.getItemWeight());//商品毛重(g)
					item.put("ItemModel", merchandise.getItemModel());//商品規格
					item.put("ItemUnit", merchandise.getItemUnit());//商品單位
				} else {
					item.put("ItemSID", merchandise.getItemSId());//商品報備編號
				}
				arrayList.add(item);//商品陣列

				iterator.remove();
			}
		}
		session.setAttribute("cart", arrayListCart);
		request.put("Items", arrayList);

		/*
		 總金額折價金額
		 */
		int discount = 0;
		request.put("Discount", discount);//必須送出請求即使暫不開放

		final String prefix = "http://localhost:8080/iCarry/";

		/*
		 成功頁面位置
		 */
		String successURL = prefix.concat("success/".concat(packet.getId().toString().concat("/")));
		request.put("SuccessURL", successURL);

		/*
		 失敗頁面位置
		 */
		String failureURL = prefix.concat("failure/".concat(packet.getId().toString().concat("/")));
		request.put("FailureURL", failureURL);

		/*
		 時間戳
		 */
		long timestamp = gregorianCalendar.getTimeInMillis() / 1000;
		request.put("timestamp", timestamp);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(merchantId).append(orderNo);
		for (HashMap<String, Object> item : arrayList) {
			stringBuilder.append(item.get("ItemAmount")).append(item.get("ItemPrice"));
			if (item.get("ItemSID") == null) {
				stringBuilder.append(item.get("ItemWeight"));
			} else {
				stringBuilder.append(item.get("ItemSID"));
			}
			stringBuilder.append(item.get("ItemNO"));
		}
		stringBuilder.append(discount).append(successURL).append(failureURL).append(timestamp).append(merchantKey);
		String string = stringBuilder.toString();
		request.put("CheckCode", org.apache.commons.codec.digest.DigestUtils.sha256Hex(string));
		System.err.println(new JSONObject(request));

		RestTemplate restTemplate = new RestTemplate();
		//HashMap<String, Object> response = restTemplate.postForObject(new URI("http://demo.cctech-support.com/icarry-as/api/B2CSetTransaction"), request, HashMap.class);
		HashMap<String, Object> response = restTemplate.postForObject(new URI("http://icarry.me/icarry-as/api/B2CSetTransaction"), request, HashMap.class);
		JSONObject jsonObjectResponse = new JSONObject(response);
		System.err.println(jsonObjectResponse);

		String accessToken = null, transactionNo = null;
		ConceptTechHistory conceptTechHistory = new ConceptTechHistory(packet);
		if (jsonObjectResponse.has("AccessToken")) {
			accessToken = jsonObjectResponse.getString("AccessToken");
			conceptTechHistory.setAccessToken(accessToken);
		}
		if (jsonObjectResponse.has("TransactionNO")) {
			transactionNo = jsonObjectResponse.getString("TransactionNO");
			conceptTechHistory.setTransactionNo(transactionNo);
		}
		conceptTechHistory.setTimestamp(jsonObjectResponse.getLong("Timestamp"));
		conceptTechHistory.setRtnCode(jsonObjectResponse.getLong("rtnCode"));
		conceptTechHistory.setRtnMsg(jsonObjectResponse.getString("rtnMsg"));
		conceptTechHistoryRepository.saveAndFlush(conceptTechHistory);

		return jsonObjectResponse.toString();
	}
}
