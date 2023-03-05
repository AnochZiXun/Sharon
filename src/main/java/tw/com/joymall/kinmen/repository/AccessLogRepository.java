package tw.com.joymall.kinmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tw.com.joymall.kinmen.entity.AccessLog;

/**
 * logs server access
 *
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long>, JpaSpecificationExecutor<AccessLog> {
}
