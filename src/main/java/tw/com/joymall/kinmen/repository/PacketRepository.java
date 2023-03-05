package tw.com.joymall.kinmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.com.joymall.kinmen.entity.Packet;
import tw.com.joymall.kinmen.entity.PacketStatus;
import tw.com.joymall.kinmen.entity.Regular;
import tw.com.joymall.kinmen.entity.Staff;

/**
 * 訂單
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface PacketRepository extends JpaRepository<Packet, Long>, JpaSpecificationExecutor<Packet> {

	/**
	 * @param orderNo 特店訂單編號
	 * @return 計數
	 */
	public long countByOrderNo(@Param("orderNo") String orderNo);

	/**
	 * @param orderNo 特店訂單編號
	 * @return 訂單
	 */
	public Packet findOneByOrderNo(@Param("orderNo") String orderNo);

	/**
	 * @param booth 攤商
	 * @param pageable 可分頁
	 * @return 訂單們
	 */
	public Page<Packet> findByBoothOrderByTimestampDesc(@Param("booth") Staff booth, Pageable pageable);

	/**
	 * @param booth 攤商
	 * @param packetStatus 訂單狀態
	 * @param pageable 可分頁
	 * @return 訂單們
	 */
	public Page<Packet> findByBoothAndPacketStatusOrderByTimestampDesc(@Param("booth") Staff booth, @Param("packetStatus") PacketStatus packetStatus, Pageable pageable);

	/**
	 * @param regular 會員
	 * @param pageable 可分頁
	 * @return 訂單們
	 */
	public Page<Packet> findByRegularOrderByTimestampDesc(@Param("regular") Regular regular, Pageable pageable);
}
