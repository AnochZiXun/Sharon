package tw.com.joymall.kinmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.joymall.kinmen.entity.ConceptTechHistory;

/**
 * iCarry支付歷程
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface ConceptTechHistoryRepository extends JpaRepository<ConceptTechHistory, Long> {
}
