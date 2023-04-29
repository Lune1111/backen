import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listUser(query) {
  return request({
    url: '/app/user/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getUser(id) {
  return request({
    url: '/app/user/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addUser(data) {
  return request({
    url: '/app/user',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateUser(data) {
  return request({
    url: '/app/user',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delUser(id) {
  return request({
    url: '/app/user/' + id,
    method: 'delete'
  })
}
