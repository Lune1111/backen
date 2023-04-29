import request from '@/utils/request'

// 查询关系列表
export function listRelation(query) {
  return request({
    url: '/app/relation/list',
    method: 'get',
    params: query
  })
}

// 新增关系
export function addRelation(data) {
  return request({
    url: '/app/relation',
    method: 'post',
    data: data
  })
}
