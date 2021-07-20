export default [{
  path: '/svcs',
  component: () => import('../templates/LeftTemplate.vue'),
  children: [{
    name: 'svcs',
    path: '',
    component: () => import('./views/SvcList.vue')
  }, {
    name: 'svc',
    path: ':id',
    props: true,
    component: () => import('./views/Svc.vue')
  }, {
    name: 'svcView',
    path: 'viewer/:svcId',
    props: true,
    component: () => import('./views/SvcViewer.vue')
  }]
}]
