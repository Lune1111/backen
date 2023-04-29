import request from '@/utils/request'

// 查询话题列表
export function listSubject(query) {
  return request({
    url: '/app/subject/list',
    method: 'get',
    params: query
  })
}

// 查询话题详细
export function getSubject(id) {
  return request({
    url: '/app/subject/' + id,
    method: 'get'
  })
}

// 新增话题
export function addSubject(data) {
  return request({
    url: '/app/subject',
    method: 'post',
    data: data
  })
}

// 修改话题
export function updateSubject(data) {
  return request({
    url: '/app/subject',
    method: 'put',
    data: data
  })
}

// 删除话题
export function delSubject(id) {
  return request({
    url: '/app/subject/' + id,
    method: 'delete'
  })
}
