import request from '@/utils/request'

// 查询点赞列表
export function listLikes(query) {
  return request({
    url: '/app/likes/list',
    method: 'get',
    params: query
  })
}

// 查询点赞详细
export function getLikes(id) {
  return request({
    url: '/app/likes/' + id,
    method: 'get'
  })
}

// 新增点赞
export function addLikes(data) {
  return request({
    url: '/app/likes',
    method: 'post',
    data: data
  })
}

// 修改点赞
export function updateLikes(data) {
  return request({
    url: '/app/likes',
    method: 'put',
    data: data
  })
}

// 删除点赞
export function delLikes(id) {
  return request({
    url: '/app/likes/' + id,
    method: 'delete'
  })
}
