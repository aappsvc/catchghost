package com.rickqin.catchghost.takephoto;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Rick Qin <rickqinj@gmail.com>
 */
interface PhotoRepository extends JpaRepository<PhotoEntity, Long>, JpaSpecificationExecutor<PhotoEntity> {
    
    List<PhotoEntity> findByWorkorder(String workorder);
    
}
