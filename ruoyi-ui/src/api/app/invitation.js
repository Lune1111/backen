import request from '@/utils/request'

// 按条件查询帖子列表
export function listInvitation(query) {
  return request({
    url: '/app/invitation/list',
    method: 'get',
    params: query
  })
}

// 查询【帖子】详细
export function getInvitation(id) {
  return request({
    url: '/app/invitation/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addInvitation(data) {
  return request({
    url: '/app/invitation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateInvitation(data) {
  return request({
    url: '/app/invitation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delInvitation(id) {
  return request({
    url: '/app/invitation/' + id,
    method: 'delete'
  })
}
