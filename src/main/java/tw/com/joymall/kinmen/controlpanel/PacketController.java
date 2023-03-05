package tw.com.joymall.kinmen.controlpanel;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tw.com.joymall.kinmen.Utils;
import tw.com.joymall.kinmen.entity.Cart;
import tw.com.joymall.kinmen.entity.Packet;
import tw.com.joymall.kinmen.entity.Regular;
import tw.com.joymall.kinmen.entity.Staff;
import tw.com.joymall.kinmen.repository.CartRepository;
import tw.com.joymall.kinmen.repository.PacketRepository;
import tw.com.joymall.kinmen.repository.RegularRepository;
import tw.com.joymall.kinmen.repository.StaffRepository;
import tw.com.joymall.kinmen.service.Services;

/**
 * 訂單明細
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Controller
@RequestMapping("/cPanel/packet")
public class PacketController {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PacketRepository packetRepository;

	@Autowired
	private RegularRepository regularRepository;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.TAIWAN);

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private Services services;

	/**
	 * 列表
	 *
	 * @param size 每頁幾筆
	 * @param number 第幾頁
	 * @param request
	 * @param response
	 * @param session
	 * @return 網頁
	 */
	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView welcome(@RequestParam(value = "s", defaultValue = "10") Integer size, @RequestParam(value = "p", defaultValue = "0") Integer number, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParserConfigurationException, SQLException {
		Integer me = (Integer) session.getAttribute("me");
		if (me != null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//禁止會員存取
			return null;
		}
		Staff myself = staffRepository.findOneByLogin(request.getRemoteUser());
		if (myself == null || !myself.isInternal()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//禁止攤商存取
			return null;
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "internal", Boolean.toString(myself.isInternal()));
		services.buildAsideElement(document, request);

		/*
		 分頁
		 */
		Element paginationElement = Utils.createElementWithAttribute("pagination", documentElement, "action", request.getRequestURI());
		if (size < 1) {
			size = 10;//每頁幾筆
		}
		if (number < 0) {
			number = 0;//第幾頁
		}
		Pageable pageRequest = new PageRequest(number, size, Sort.Direction.DESC, "id");
		Page<Regular> pageOfEntities;
		pageOfEntities = regularRepository.findAll(pageRequest);
		number = pageOfEntities.getNumber();
		Integer totalPages = pageOfEntities.getTotalPages();
		Long totalElements = pageOfEntities.getTotalElements();
		if (pageOfEntities.hasPrevious()) {
			paginationElement.setAttribute("previous", Integer.toString(number - 1));
			if (!pageOfEntities.isFirst()) {
				paginationElement.setAttribute("first", "0");
			}
		}
		paginationElement.setAttribute("size", size.toString());
		paginationElement.setAttribute("number", number.toString());
		for (Integer i = 0; i < totalPages; i++) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", paginationElement, Integer.toString(i + 1), "value", i.toString());
			if (number.equals(i)) {
				optionElement.setAttribute("selected", null);
			}
		}
		paginationElement.setAttribute("totalPages", totalPages.toString());
		paginationElement.setAttribute("totalElements", totalElements.toString());
		if (pageOfEntities.hasNext()) {
			paginationElement.setAttribute("next", Integer.toString(number + 1));
			if (!pageOfEntities.isLast()) {
				paginationElement.setAttribute("last", Integer.toString(totalPages - 1));
			}
		}

		/*
		 列表
		 */
		Element listElement = Utils.createElement("list", documentElement);
		for (Regular entity : pageOfEntities.getContent()) {
			Element rowElement = Utils.createElement("row", listElement);
			Utils.createElementWithTextContent("id", rowElement, entity.getId().toString());
			Utils.createElementWithTextContent("lastname", rowElement, entity.getLastname());
			Utils.createElementWithTextContent("firstname", rowElement, entity.getFirstname());
			Utils.createElementWithTextContent("email", rowElement, entity.getEmail());
			Utils.createElementWithTextContent("birth", rowElement, simpleDateFormat.format(entity.getBirth()));
			Utils.createElementWithTextContent("gender", rowElement, Boolean.toString(entity.getGender()));
			Utils.createElementWithTextContent("available", rowElement, Boolean.toString(entity.getShadow().matches("^[a-z0-9]{128}$")));
		}

		ModelAndView modelAndView = new ModelAndView("cPanel/regular");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@RequestMapping(value = "/{id:\\d+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView packet(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParserConfigurationException, SQLException {
		Integer me = (Integer) session.getAttribute("me");
		if (me != null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//禁止會員存取
			return null;
		}
		Staff myself = staffRepository.findOneByLogin(request.getRemoteUser());
		if (myself == null || myself.isInternal()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//禁止工作人員存取
			return null;
		}
		Packet packet = packetRepository.findOne(id);
		if (packet == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//找不到
			return null;
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "internal", Boolean.toString(myself.isInternal()));
		services.buildAsideElement(document, request);

		String orderNo = packet.getOrderNo();
		Date timestamp = packet.getTimestamp();
		Element elementPacket = Utils.createElement("packet", documentElement);
		Utils.createElementWithTextContent("orderNo", elementPacket, orderNo == null ? "" : orderNo);
		Utils.createElementWithTextContent("timestamp", elementPacket, timestamp == null ? "" : simpleDateFormat.format(timestamp));

		Integer total = 0;
		Element elementList = Utils.createElement("list", documentElement);
		for (Cart cart : cartRepository.findByPacket(packet)) {
			String specification = cart.getSpecification();
			Integer price = cart.getPrice();
			total += price;

			Element elementRow = Utils.createElement("row", elementList);
			Utils.createElementWithTextContent("merchandiseName", elementRow, cart.getMerchandise().getName());
			Utils.createElementWithTextContent("merchandiseSpecification", elementRow, specification == null ? "" : specification);
			Utils.createElementWithTextContent("quantity", elementRow, Short.toString(cart.getQuantity()));
			Utils.createElementWithTextContent("price", elementRow, price.toString());
		}
		Utils.createElementWithTextContent("total", elementPacket, total.toString());

		ModelAndView modelAndView = new ModelAndView("cPanel/packet");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
