package cn.zut.core.service;

import cn.zut.common.generic.GenericResponse;
import cn.zut.dao.entity.BusinessServiceEntity;

import java.util.List;

/**
 * @author LiuBowen
 */
public interface BusinessService {
    /**
     * 添加申请服务
     */
    GenericResponse addService(BusinessServiceEntity businessServiceEntity);

    /**
     * 查看所有服务
     */
    List<BusinessServiceEntity> serviceList();

    /**
     * 同意服务
     */
    boolean agreeService(int serviceId);

    /**
     * 不同意服务操作
     */
    boolean disAgreeService(int serviceId);
}