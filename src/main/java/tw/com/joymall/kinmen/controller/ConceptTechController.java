package tw.com.joymall.kinmen.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tw.com.joymall.kinmen.Utils;
import tw.com.joymall.kinmen.entity.Cart;
import tw.com.joymall.kinmen.entity.Merchandise;
import tw.com.joymall.kinmen.entity.Packet;
import tw.com.joymall.kinmen.entity.Regular;
import tw.com.joymall.kinmen.entity.Staff;
import tw.com.joymall.kinmen.repository.CartRepository;
import tw.com.joymall.kinmen.repository.PacketRepository;
import tw.com.joymall.kinmen.repository.PacketStatusRepository;
import tw.com.joymall.kinmen.repository.RegularRepository;
import tw.com.joymall.kinmen.service.Services;

/**
 * 柯思科技跨境電商
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Controller
@RequestMapping("/iCarry")
public class ConceptTechController {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PacketRepository packetRepository;

	@Autowired
	private PacketStatusRepository packetStatusRepository;

	@Autowired
	private RegularRepository regularRepository;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.TAIWAN);

	@Autowired
	private Services services;

	/**
	 * 交易成功
	 *
	 * @param packetId 訂單的主建
	 * @param request
	 * @param response
	 * @param session
	 * @return 網頁
	 */
	@RequestMapping(value = "/success/{packetId:\\d+}/", method = RequestMethod.GET)
	@SuppressWarnings("ConvertToTryWithResources")
	private ModelAndView success(@PathVariable Long packetId, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
		Packet packet = packetRepository.findOne(packetId);
		if (packet == null) {
			System.err.println(getClass().getCanonicalName() + "\n找不到訂單！");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return null;
		}
		Staff booth = packet.getBooth();
		Regular regular = packet.getRegular();
		String orderNo = packet.getOrderNo();
		Date timestamp = packet.getTimestamp();

		if (request.getRemoteUser() != null) {
			System.err.println(getClass().getCanonicalName() + "\n已登入後臺！");
			return new ModelAndView("redirect:/");
		}
		Integer me = (Integer) session.getAttribute("me");
		if (me == null) {
			System.err.println(getClass().getCanonicalName() + "\n會員未登入！");
			return new ModelAndView("redirect:/");
		}
		if (!Objects.equals(packet.getRegular(), regularRepository.findOne(me))) {
			System.err.println(getClass().getCanonicalName() + "\n此訂單並非此會員的！");
			return new ModelAndView("redirect:/");
		}

		if (packet.getPacketStatus() == null) {
			packet.setPacketStatus(packetStatusRepository.findOne((short) 1));//訂單狀態→備貨中
			packetRepository.saveAndFlush(packet);

			Document document = Utils.newDocument();
			Element documentElement = Utils.createElement("document", document);
			Utils.createElementWithTextContent("regular", documentElement, regular.getLastname() + regular.getFirstname());
			Utils.createElementWithTextContent("booth", documentElement, booth.getName());
			Utils.createElementWithTextContent("orderNo", documentElement, orderNo == null ? "" : orderNo);
			Utils.createElementWithTextContent("timestamp", documentElement, timestamp == null ? "" : simpleDateFormat.format(timestamp));

			Integer total = 0;
			Element elementPacket = Utils.createElement("packet", documentElement);
			for (Cart cart : cartRepository.findByPacket(packet)) {
				Merchandise merchandise = cart.getMerchandise();
				String specification = cart.getSpecification();
				short quantity = cart.getQuantity();
				Integer price = merchandise.getPrice(), subTotal = price * quantity;
				total += subTotal;

				Element elementCart = Utils.createElementWithTextContent("cart", elementPacket, merchandise.getName());
				if (specification != null) {
					elementCart.setAttribute("specification", specification);
				}
				elementCart.setAttribute("price", price.toString());
				elementCart.setAttribute("quantity", Short.toString(quantity));
				elementCart.setAttribute("subTotal", subTotal.toString());
			}
			Utils.createElementWithTextContent("total", documentElement, total.toString());

			StringWriter stringWriter = new StringWriter();
			TransformerFactory.newInstance().newTransformer(new StreamSource(getClass().getResourceAsStream("/iCarrySuccess.xsl"))).transform(new DOMSource(document), new StreamResult(stringWriter));
			stringWriter.flush();
			stringWriter.close();
			System.err.println(stringWriter.toString());
			try {
				services.buildHtmlEmail(
					packet.getRegular().getEmail(),
					packet.getBooth().getLogin(),
					"金門跨境電子商務訂單#" + (orderNo == null ? "" : orderNo) + "交易成功",
					stringWriter.toString()
				).send();
			} catch (EmailException emailException) {
				System.err.println(getClass().getCanonicalName() + ":\n" + emailException.getLocalizedMessage());
				emailException.printStackTrace(System.err);
			}
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "requestURI", request.getRequestURI());
		documentElement.setAttribute("me", me.toString());
		services.buildFooterElement(documentElement);//底部

		ModelAndView modelAndView = new ModelAndView("iCarrySuccess");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 交易失敗
	 *
	 * @param packetId 訂單的主建
	 * @param request
	 * @param response
	 * @param session
	 * @return 網頁
	 */
	@RequestMapping(value = "/failure/{packetId:\\d+}/", method = RequestMethod.GET)
	@SuppressWarnings("ConvertToTryWithResources")
	private ModelAndView failure(@PathVariable Long packetId, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
		Packet packet = packetRepository.findOne(packetId);
		if (packet == null) {
			System.err.println(getClass().getCanonicalName() + "\n找不到訂單！");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return null;
		}
		Staff booth = packet.getBooth();
		Regular regular = packet.getRegular();
		String orderNo = packet.getOrderNo();
		Date timestamp = packet.getTimestamp();

		if (request.getRemoteUser() != null) {
			System.err.println(getClass().getCanonicalName() + "\n已登入後臺！");
			return new ModelAndView("redirect:/");
		}
		Integer me = (Integer) session.getAttribute("me");
		if (me == null) {
			System.err.println(getClass().getCanonicalName() + "\n會員未登入！");
			return new ModelAndView("redirect:/");
		}
		if (!Objects.equals(packet.getRegular(), regularRepository.findOne(me))) {
			System.err.println(getClass().getCanonicalName() + "\n此訂單並非此會員的！");
			return new ModelAndView("redirect:/");
		}

		if (packet.getPacketStatus() == null) {
			packet.setPacketStatus(packetStatusRepository.findOne((short) -1));//訂單狀態→被取消
			packetRepository.saveAndFlush(packet);

			Document document = Utils.newDocument();
			Element documentElement = Utils.createElement("document", document);
			Utils.createElementWithTextContent("regular", documentElement, regular.getLastname() + regular.getFirstname());
			Utils.createElementWithTextContent("booth", documentElement, booth.getName());
			Utils.createElementWithTextContent("orderNo", documentElement, orderNo == null ? "" : orderNo);
			Utils.createElementWithTextContent("timestamp", documentElement, timestamp == null ? "" : simpleDateFormat.format(timestamp));

			Integer total = 0;
			Element elementPacket = Utils.createElement("packet", documentElement);
			for (Cart cart : cartRepository.findByPacket(packet)) {
				Merchandise merchandise = cart.getMerchandise();
				String specification = cart.getSpecification();
				short quantity = cart.getQuantity();
				Integer price = merchandise.getPrice(), subTotal = price * quantity;
				total += subTotal;

				Element elementCart = Utils.createElementWithTextContent("cart", elementPacket, merchandise.getName());
				if (specification != null) {
					elementCart.setAttribute("specification", specification);
				}
				elementCart.setAttribute("price", price.toString());
				elementCart.setAttribute("quantity", Short.toString(quantity));
				elementCart.setAttribute("subTotal", subTotal.toString());
			}
			Utils.createElementWithTextContent("total", documentElement, total.toString());

			StringWriter stringWriter = new StringWriter();
			TransformerFactory.newInstance().newTransformer(new StreamSource(getClass().getResourceAsStream("/iCarryFailure.xsl"))).transform(new DOMSource(document), new StreamResult(stringWriter));
			stringWriter.flush();
			stringWriter.close();
			System.err.println(stringWriter.toString());
			try {
				services.buildHtmlEmail(
					packet.getRegular().getEmail(),
					packet.getBooth().getLogin(),
					"金門跨境電子商務訂單#" + (orderNo == null ? "" : orderNo) + "交易失敗",
					stringWriter.toString()
				).send();
			} catch (EmailException emailException) {
				System.err.println(getClass().getCanonicalName() + ":\n" + emailException.getLocalizedMessage());
				emailException.printStackTrace(System.err);
			}
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "requestURI", request.getRequestURI());
		documentElement.setAttribute("me", me.toString());
		services.buildFooterElement(documentElement);//底部

		ModelAndView modelAndView = new ModelAndView("iCarryFailure");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
