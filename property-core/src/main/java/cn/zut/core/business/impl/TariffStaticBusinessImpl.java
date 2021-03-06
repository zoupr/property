package cn.zut.core.business.impl;

import cn.zut.common.dao.PageModel;
import cn.zut.common.exception.ExceptionCode;
import cn.zut.common.exception.ExceptionMessage;
import cn.zut.common.generic.GenericResponse;
import cn.zut.common.generic.SimplePageResult;
import cn.zut.common.util.DateUtil;
import cn.zut.core.business.TariffStaticBusiness;
import cn.zut.dao.entity.TariffCompanyEntity;
import cn.zut.dao.entity.TariffStandardEntity;
import cn.zut.dao.persistence.TariffCompanyMapper;
import cn.zut.dao.persistence.TariffStandardMapper;
import cn.zut.facade.request.TariffCompanyRequest;
import cn.zut.facade.request.TariffStandardRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: property
 * DESCRIPTION: 类描述
 *
 * @author DaoyuanWang
 * @date 2018/3/24
 */
@Component("tariffStaticBusiness")
public class TariffStaticBusinessImpl implements TariffStaticBusiness {

    @Resource
    private TariffCompanyMapper tariffCompanyMapper;
    @Resource
    private TariffStandardMapper tariffStandardMapper;

    @Override
    public GenericResponse addCompany(TariffCompanyRequest tariffCompanyRequest) {
        TariffCompanyEntity tariffCompanyEntity = new TariffCompanyEntity();
        BeanUtils.copyProperties(tariffCompanyRequest, tariffCompanyEntity);
        tariffCompanyEntity.setStatus(true);
        tariffCompanyEntity.setCreateTime(new Date());
        tariffCompanyMapper.insert(tariffCompanyEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse modifyCompany(TariffCompanyRequest tariffCompanyRequest) {
        TariffCompanyEntity tariffCompanyEntity = new TariffCompanyEntity();
        BeanUtils.copyProperties(tariffCompanyRequest, tariffCompanyEntity);
        tariffCompanyEntity.setUpdateTime(new Date());
        tariffCompanyMapper.update(tariffCompanyEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse getCompany(TariffCompanyRequest tariffCompanyRequest) {
        TariffCompanyEntity tariffCompanyEntity = new TariffCompanyEntity();
        BeanUtils.copyProperties(tariffCompanyRequest, tariffCompanyEntity);
        tariffCompanyMapper.selectByExample(tariffCompanyEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse getCompanyList(TariffCompanyRequest tariffCompanyRequest) {
        TariffCompanyEntity tariffCompanyEntity = new TariffCompanyEntity();
        BeanUtils.copyProperties(tariffCompanyRequest, tariffCompanyEntity);
//        tariffCompanyMapper.selectByExample(tariffCompanyEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse removeCompany(Long companyId) {
        TariffCompanyEntity tariffCompanyEntity = new TariffCompanyEntity();
        tariffCompanyEntity.setCompanyId(companyId);
        tariffCompanyEntity.setStatus(false);
        tariffCompanyEntity.setUpdateTime(new Date());
        tariffCompanyMapper.update(tariffCompanyEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse addStandard(TariffStandardRequest tariffStandardRequest) {
        TariffStandardEntity tariffStandardEntity = new TariffStandardEntity();
        tariffStandardEntity.setBusiness(tariffStandardRequest.getBusiness());
        tariffStandardEntity.setLevel(tariffStandardRequest.getLevel());
        tariffStandardEntity = tariffStandardMapper.selectByExample(tariffStandardEntity);
        if (tariffStandardEntity != null) {
            return new GenericResponse(new ExceptionMessage(ExceptionCode.TARIFF_STANDARD_IS_EXIST));
        }

        tariffStandardEntity = new TariffStandardEntity();
        BeanUtils.copyProperties(tariffStandardRequest, tariffStandardEntity);

        if (!DateUtil.checkDateSize(tariffStandardEntity.getStartTime(), tariffStandardEntity.getEndTime())) {
            return new GenericResponse(new ExceptionMessage(ExceptionCode.LAST_DATE_NOT_SMALL_PRE_DATE));
        }

        tariffStandardEntity.setStartTime(DateUtil.getDayStartTime(tariffStandardEntity.getStartTime()));
        tariffStandardEntity.setEndTime(DateUtil.getDayEndTime(tariffStandardEntity.getEndTime()));
        tariffStandardEntity.setStatus(true);
        tariffStandardEntity.setCreateTime(new Date());
        tariffStandardMapper.insert(tariffStandardEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse modifyStandard(TariffStandardRequest tariffStandardRequest) {
        TariffStandardEntity tariffStandardEntity = new TariffStandardEntity();
        BeanUtils.copyProperties(tariffStandardRequest, tariffStandardEntity);
        tariffStandardEntity.setUpdateTime(new Date());
        tariffStandardMapper.update(tariffStandardEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse getStandard(TariffStandardRequest tariffStandardRequest) {
        TariffStandardEntity tariffStandardEntity = new TariffStandardEntity();
        BeanUtils.copyProperties(tariffStandardRequest, tariffStandardEntity);
        tariffStandardMapper.selectByExample(tariffStandardEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse getStandardList(TariffStandardRequest tariffStandardRequest) {
        TariffStandardEntity tariffStandardEntity = new TariffStandardEntity();
        BeanUtils.copyProperties(tariffStandardRequest, tariffStandardEntity);
//        tariffStandardMapper.selectListPageByExample(tariffStandardEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse removeStandard(Long standardId) {
        TariffStandardEntity tariffStandardEntity = new TariffStandardEntity();
        tariffStandardEntity.setStandardId(standardId);
        tariffStandardEntity.setStatus(false);
        tariffStandardEntity.setUpdateTime(new Date());
        tariffStandardMapper.update(tariffStandardEntity);
        return GenericResponse.SUCCESS;
    }

    @Override
    public SimplePageResult<TariffCompanyEntity> pageCompanyByModel(PageModel<TariffCompanyEntity> pageModel) {
        List<TariffCompanyEntity> tariffCompanyEntities = tariffCompanyMapper.selectListPageByExample(pageModel);
        int memberCount = tariffCompanyMapper.selectCountByExample(pageModel.getSearch());
        SimplePageResult<TariffCompanyEntity> pageResult = new SimplePageResult<>();
        // 总记录数量 || 记录数据列表 || 页码 || 记录数量
        pageResult.setTotal(memberCount);
        pageResult.setRows(tariffCompanyEntities);
        pageResult.setPage(pageModel.getPage());
        pageResult.setSize(pageModel.getRows());
        return pageResult;
    }

    @Override
    public SimplePageResult<TariffStandardEntity> pageStandardByModel(PageModel<TariffStandardEntity> pageModel) {
        List<TariffStandardEntity> tariffStandardEntities = tariffStandardMapper.selectListPageByExample(pageModel);
        int countByExample = tariffStandardMapper.selectCountByExample(pageModel.getSearch());
        SimplePageResult<TariffStandardEntity> pageResult = new SimplePageResult<>();
        // 总记录数量 || 记录数据列表 || 页码 || 记录数量
        pageResult.setTotal(countByExample);
        pageResult.setRows(tariffStandardEntities);
        pageResult.setPage(pageModel.getPage());
        pageResult.setSize(pageModel.getRows());
        return pageResult;
    }
}
