export default [{
  path: '/cnn',
  component: (): Promise<any> => import('../templates/LeftTemplate.vue'),
  children: [{
    path: '',
    name: 'cnnList',
    component: (): Promise<any> => import('./views/CnnList.vue')
  }, {
    path: ':id',
    name: 'cnn',
    props: true,
    component: (): Promise<any> => import('./views/Cnn.vue')
  }]
}]
