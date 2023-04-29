import request from '@/utils/request'

// 查询评论列表
export function listComment(query) {
  return request({
    url: '/app/comment/list',
    method: 'get',
    params: query
  })
}

// 新增评论
export function addComment(data) {
  return request({
    url: '/app/comment',
    method: 'post',
    data: data
  })
}

// 删除评论
export function delComment(id) {
  return request({
    url: '/app/comment/' + id,
    method: 'delete'
  })
}
