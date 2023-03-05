package tw.com.joymall.kinmen.entity;

import javax.persistence.*;

/**
 * iCarry支付歷程
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ConceptTechHistory.findAll", query = "SELECT cth FROM ConceptTechHistory cth")
})
@Table(catalog = "\"KinMenDB\"", schema = "\"public\"", name = "\"ConceptTechHistory\"")
public class ConceptTechHistory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false, name = "\"id\"")
	private Long id;

	@JoinColumn(name = "\"packet\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Packet packet;

	@Column(name = "\"accessToken\"")
	private String accessToken;

	@Column(name = "\"transactionNo\"")
	private String transactionNo;

	@Column(name = "\"timestamp\"")
	private Long timestamp;

	@Column(name = "\"rtnCode\"")
	private Long rtnCode;

	@Column(name = "\"rtnMsg\"")
	private String rtnMsg;

	@Column(name = "\"checkCode\"")
	private String checkCode;

	/**
	 * 建構子
	 */
	public ConceptTechHistory() {
	}

	/**
	 * @param id 主鍵
	 */
	protected ConceptTechHistory(Long id) {
		this.id = id;
	}

	/**
	 * @param packet 訂單
	 */
	public ConceptTechHistory(Packet packet) {
		this.packet = packet;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof ConceptTechHistory)) {
			return false;
		}
		ConceptTechHistory other = (ConceptTechHistory) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.com.e95.entity.AllPayHistory[ id=" + id + " ]";
	}

	/**
	 * @return 主鍵
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id 主鍵
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return 訂單
	 */
	public Packet getPacket() {
		return packet;
	}

	/**
	 * @param packet 訂單
	 */
	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	/**
	 * @return 一次性交付密碼
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken 一次性交付密碼
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return 交易編號
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * @param transactionNo 交易編號
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * @return 訂單建立時間戳
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp 訂單建立時間戳
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return 回傳代碼
	 */
	public Long getRtnCode() {
		return rtnCode;
	}

	/**
	 * @param rtnCode 回傳代碼
	 */
	public void setRtnCode(Long rtnCode) {
		this.rtnCode = rtnCode;
	}

	/**
	 * @return 回傳訊息
	 */
	public String getRtnMsg() {
		return rtnMsg;
	}

	/**
	 * @param rtnMsg 回傳訊息
	 */
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	/**
	 * @return 資料檢查碼
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * @param checkCode 資料檢查碼
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
