package com.ruoyi.common.core.domain.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.app.QxInvitation;
import com.ruoyi.common.core.domain.app.QxSubject;
import com.ruoyi.common.core.domain.app.QxUser;
import com.ruoyi.common.utils.uuid.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 【请填写功能名称】对象 qx_invitation
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QxInvitationVo extends QxInvitation {
    private static final long serialVersionUID = 1L;

    /** 用户信息 */
    @Excel(name = "用户信息")
    private QxUser qxUser;
    /** 用户信息 */
    @Excel(name = "话题信息")
    private List<QxSubject> qxSubjectList;
    /** 点赞信息 */
    @Excel(name = "点赞信息")
    private String islike;
    /** 点赞ID */
    @Excel(name = "点赞ID")
    private Long likeId;
    /** 图片集合 */
    @Excel(name = "图片集合")
    private List<String> photoList;
    /** 内容发布时间 */
    @Excel(name = "内容发布时间")
    private String date;
    /** 评论人信息 */
    @Excel(name = "评论人信息")
    private String othersId;

    /** 是否举报 */
    @Excel(name = "是否举报")
    private Long isReport;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("qxUser",getQxUser().toString())
                .append("qxSubject",getQxSubjectList().toString())
                .append("photoList",getPhotoList())
                .append("date",getDate())
                .toString();
    }
}
