package tw.com.joymall.kinmen.entity;

import java.util.*;
import javax.persistence.*;

/**
 * 訂單
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Packet.findAll", query = "SELECT p FROM Packet p")
})
@Table(catalog = "\"KinMenDB\"", schema = "\"public\"", name = "\"Packet\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"orderNo\""})
})
public class Packet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false, name = "\"id\"")
	private Long id;

	@JoinColumn(name = "\"booth\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne
	private Staff booth;

	@JoinColumn(name = "\"regular\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Regular regular;

	@JoinColumn(name = "\"packetStatus\"", referencedColumnName = "\"id\"")
	@ManyToOne
	private PacketStatus packetStatus;

	@Column(name = "\"orderNo\"")
	private String orderNo;

	@Column(name = "\"timestamp\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name = "\"checkCode\"")
	private String checkCode;

	/**
	 * 建構子
	 */
	public Packet() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Packet(Long id) {
		this.id = id;
	}

	/**
	 * @param booth 攤商
	 * @param regular 會員
	 * @param orderNo 交易編號
	 * @param timestamp 交易時間
	 */
	public Packet(Regular regular, Staff booth, String orderNo, Date timestamp) {
		this.regular = regular;
		this.booth = booth;
		this.orderNo = orderNo;
		this.timestamp = timestamp;
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
		if (!(object instanceof Packet)) {
			return false;
		}
		Packet other = (Packet) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.com.e95.entity.Packet[ id=" + id + " ]";
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
	 * @return 攤商
	 */
	public Staff getBooth() {
		return booth;
	}

	/**
	 * @param booth 攤商
	 */
	public void setBooth(Staff booth) {
		this.booth = booth;
	}

	/**
	 * @return 會員
	 */
	public Regular getRegular() {
		return regular;
	}

	/**
	 * @param regular 會員
	 */
	public void setRegular(Regular regular) {
		this.regular = regular;
	}

	/**
	 * @return 訂單狀態
	 */
	public PacketStatus getPacketStatus() {
		return packetStatus;
	}

	/**
	 * @param packetStatus 訂單狀態
	 */
	public void setPacketStatus(PacketStatus packetStatus) {
		this.packetStatus = packetStatus;
	}

	/**
	 * @return 特店訂單編號
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo 特店訂單編號
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 時間戳
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp 時間戳
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
