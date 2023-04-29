package com.ruoyi.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.app.service.IQxSubjectService;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.app.mapper.QxInvitationMapper;
import com.ruoyi.app.service.IQxInvitationService;
import com.ruoyi.common.core.domain.app.QxSubject;
import com.ruoyi.common.core.domain.app.vo.QxInvitationVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.minio.template.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Service
public class QxInvitationServiceImpl extends ServiceImpl<QxInvitationMapper, QxInvitation> implements IQxInvitationService
{
    @Autowired
    private MinioTemplate miniotemplate;

    @Autowired
    private IQxSubjectService qxSubjectService;
    /**
     * 查询【请填写功能名称】列表
     *
     *
     * @param qxInvitation 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<QxInvitation> selectQxInvitationList(QxInvitation qxInvitation)
    {
        return baseMapper.selectQxInvitationList(qxInvitation);
    }
    /**
     * 查询【帖子，关联帖子的用户】
     *  @return 【帖子，关联帖子的用户】集合
     */
    @Override
    public List<QxInvitationVo> selectQxInVo(QxInvitationVo qxinvitationvo){
        List<QxInvitationVo> qxInvitationList=new ArrayList <>();
         baseMapper.selectQxInVo(qxinvitationvo).forEach(item-> {
            if(item.getSubject()!=null) {
                List<QxSubject> qxSubjects=new ArrayList<>();
                for (String s : item.getSubject().split(",")) {
                    QxSubject qxSubject=qxSubjectService.getById(s);
                    qxSubjects.add(qxSubject);
                    item.setQxSubjectList(qxSubjects);
                }
            }
             List<String>photos= miniotemplate.getPhotoList("invitation",item.getId());
             item.setPhotoList(photos);
             item.setDate(DateUtils.verifyDate(item.getCreateTime()));
            qxInvitationList.add(item);
        });
        return qxInvitationList;
    }

    /**
     * 查询【用户点赞帖子】
     *  @return 【用户点赞帖子】集合
     */
    @Override
    public List<QxInvitationVo> selectQxInLike(QxInvitationVo qxinvitationvo){
        List<QxInvitationVo> qxInvitationList=new ArrayList <>();
        baseMapper.selectQxInLike(qxinvitationvo).forEach(item-> {
            if(item.getSubject()!=null) {
                List<QxSubject> qxSubjects=new ArrayList<>();
                for (String s : item.getSubject().split(",")) {
                    QxSubject qxSubject=qxSubjectService.getById(s);
                    qxSubjects.add(qxSubject);
                    item.setQxSubjectList(qxSubjects);
                }
            }
            List<String>photos= miniotemplate.getPhotoList("invitation",item.getId());
            item.setPhotoList(photos);
            item.setDate(DateUtils.verifyDate(item.getCreateTime()));
            qxInvitationList.add(item);
        });
        return qxInvitationList;
    };


}
