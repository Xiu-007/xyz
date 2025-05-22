import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'person', component: () => import('@/views/manager/Person.vue')},
        { path: 'dPerson', component: () => import('@/views/manager/DPerson.vue')},
        { path: 'sePerson', component: () => import('@/views/manager/SEperson.vue')},
        { path: 'tPerson', component: () => import('@/views/manager/TPerson.vue')},
        { path: 'sPerson', component: () => import('@/views/manager/SPerson.vue')},
        { path: 'password', component: () => import('@/views/manager/Password.vue')},
        { path: 'home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'teacher', component: () => import('@/views/manager/Teacher.vue')},
        { path: 'student', component: () => import('@/views/manager/Student.vue')},
        { path: 'notice', component: () => import('@/views/manager/Notice.vue')},
        { path: 'dean', component: () => import('@/views/manager/Dean.vue')},
        { path: 'secretary', component: () => import('@/views/manager/Secretary.vue')},
        { path: 'documentupload', component: () => import('@/views/manager/DocumentUpload.vue')},
        { path: 'teacherAssignment', component: () => import('@/views/manager/TeacherAssignment.vue')},
        { path: 'deanAudit', component: () => import('@/views/manager/DeanAudit.vue')},
        { path: 'teacherAudit', component: () => import('@/views/manager/TeacherAudit.vue')},
        { path: 'systemlogs', component: () => import('@/views/manager/SystemLogs.vue')},
      ]
    },
    { path: '/login', component: () => import('@/views/Login.vue')},
    { path: '/register', component: () => import('@/views/Register.vue')},
  ]
})

export default router
