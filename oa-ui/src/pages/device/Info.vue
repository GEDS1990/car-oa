<template>
  <div>
    <Card>
      <p slot="title">条件查询</p>
      <Row>
        <Col :span="2" :offset="1" style="margin-top:0.2%">
        设备名称：
        </Col>
        <Col :span="4">
        <Input type="text" v-model="queryCondition.deviceName" placeholder="请输入..."></Input>
        </Col>
        <Col :span="2" offset="1">
        <Button type="primary" shape="circle" icon="ios-search" @click="getlist">查询</Button>
        </Col>
        <Col :span="2" offset="1">
        <Button type="success" shape="circle" icon="ios-personadd"
                @click="Device={
          id: '',
          deviceName: '',
          routingDays:1,
          area:'',
          location:'',
          fromDate :new Date(),
          toDate:new Date()
        };DeviceModal.isShow=true;DeviceModal.title='新建设备';DeviceModal.disabled=false">
          新建设备
        </Button>
        </Col>
        <Col :span="3" offset="1">
        <Upload action="/v1/device/addList" ref="upload"
                :headers="header"
                :on-format-error="handleFormatError"
                :on-success="handleSuccess"
                :on-error="handleError"
                :format="['xlsx']"
        >
          <Button type="info" shape="circle" icon="ios-cloud-upload-outline">Excel文件上传</Button>
        </Upload>
        </Col>
        <Col :span="2" offset="1">
        <a  target="_blank" href="/v1/file/download">
        <Button type="warning" shape="circle" icon="ios-download-outline">Excel文件模板下载</Button>
        </a>
        </Col>
      </Row>
    </Card>
    <Table border :columns="columns" :data="list"></Table>
    <div style="margin: 10px;overflow: hidden">
      <div style="float: right;">
        <Page :total="queryCondition.total" :current.sync="queryCondition.pageNum" @on-change="getlist" show-total
              show-elevator></Page>
      </div>
    </div>
    <Modal v-model="DeviceModal.isShow"
           :title="DeviceModal.title"
    >
      <Form :model="Device" :rules="rule" :label-width="80" ref="device">
        <Form-item prop="deviceName" label="设备名：">
          <Input v-model="Device.deviceName" :disabled="DeviceModal.disabled" placeholder="请输入"/>
        </Form-item>
        <!--<Form-item label="巡检天数">-->
        <!--<InputNumber :min="1" v-model="Device.routingDays" :disabled="DeviceModal.disabled" placeholder="请输入"/>-->
        <!--</Form-item>-->
        <Form-item label="区域">
          <Input v-model="Device.area" :disabled="DeviceModal.disabled" placeholder="请输入"/>
        </Form-item>
        <Form-item label="位置">
          <Input v-model="Device.location" :disabled="DeviceModal.disabled" placeholder="请输入"/>
        </Form-item>
        <!--<Form-item label="开始时间">-->
        <!--<DatePicker type="date" :value="Device.fromDate" @on-change='Device.fromDate=arguments[0]'-->
        <!--placeholder="选择日期和时间（不含秒）"-->
        <!--style="width: 300px" :disabled="DeviceModal.disabled"-->
        <!--:clearable="false"></DatePicker>-->
        <!--</Form-item>-->
        <!--<Form-item label="结束时间">-->
        <!--<DatePicker type="date" :value="Device.toDate" @on-change='Device.toDate=arguments[0]'-->
        <!--placeholder="选择日期和时间（不含秒）"-->
        <!--style="width: 300px" :disabled="DeviceModal.disabled"-->
        <!--:clearable="false"></DatePicker>-->
        <!--</Form-item>-->
      </Form>
      <div slot="footer">
        <Button type="ghost" @click="DeviceModal.isShow=false" style="margin-left: 8px">取消</Button>
        <Button type="primary" @click="doDevice" :loading="DeviceModal.isLoading" v-if="!DeviceModal.disabled">提交
        </Button>
        <Button type="primary" @click="DeviceModal.isShow=false" v-else>确认</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import {
    getDeviceList,
    addDevcie,
    updateDevcie,
    delDevcie,
    downFile
  } from '../../api/device/device'

  export default {
    data() {
      return {
        list: [],
        header: {"Authorization": sessionStorage.getItem("Authorization")},
        queryCondition: {
          pageSize: 10,
          pageNum: 1,
          deviceName: '',
          total: 0
        },
        columns: [
          {
            title: '编号',
            key: 'id'
          },
//          {
//            title: '巡检天数',
//            key: 'routingDays'
//          },
          {
            title: '设备名称',
            key: 'deviceName'
          },
          {
            title: '区域',
            key: 'area'
          },
          {
            title: '位置',
            key: 'location'
          },
//          {
//            title: '开始时间',
//            key: 'fromDate'
//          },
//          {
//            title: '结束时间',
//            key: 'toDate'
//          },
          {
            title: '操作',
            width: 320,
            render: (h, params) => h('div', [
              h('Button', {
                props: {
                  type: 'info'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.showDevice('修改设备', params)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'primary'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.showDevice('设备详情', params)

                  }
                }
              }, '详情'),
              h('Button', {
                props: {
                  type: 'success'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.$router.push({path: 'service', query: {deviceId: params.row.id}})
                  }
                }
              }, '服务列表'),
              h('Button', {
                props: {
                  type: 'error'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '是否删除',
                      content: '<p>' + params.row.deviceName + '</p>',
                      loading: true,
                      onOk: () => {
                        delDevcie(params.row.id).then(res => {
                          this.$Message.success(res.data.message)
                          this.$Modal.remove()
                          this.getlist()
                        })

                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        ],
        rule: {
          deviceName: [
            {required: true, message: '不可为空', trigger: 'blur'}
          ],
        },
        DeviceModal: {
          title: '',
          isShow: false,
          isLoading: false,
          disabled: false
        },
        Device: {
          id: '',
          deviceName: '',
          routingDays: 1,
          area: '',
          location: ''
        },
      }
    },
    created() {
      this.getlist()
    },
    methods: {
      handleFormatError(file) {
        this.$Notice.warning({
          title: '文件类型不合法',
          desc: '请使用.xlsx文件'
        })
      },
      handleSuccess(res, file) {
        console.log(res)
        if (res.code === 1) {
          this.$Message.success(res.message)
          this.getlist()
        } else {
          this.$Message.error(res.message)
        }
        this.$refs.upload.clearFiles()
      },
      handleError(res, file) {
        this.$Message.error('新增失败')
        this.$refs.upload.clearFiles()
      },
      getlist(pageNum) {
        this.queryCondition.pageNum = !isNaN(pageNum) ? pageNum : this.queryCondition.pageNum
        getDeviceList(this.queryCondition).then(res => {
          this.queryCondition.pageNum = res.data.data.pageNum
          this.list = res.data.data.list
          this.queryCondition.total = res.data.data.total
        }).catch(err => {

        })
      },
      showDevice(title, params) {
        this.Device.id = params.row.id
        this.Device.deviceName = params.row.deviceName
        this.Device.routingDays = params.row.routingDays
        this.Device.area = params.row.area
        this.Device.location = params.row.location
        this.Device.fromDate = params.row.fromDate
        this.Device.toDate = params.row.toDate
        this.DeviceModal.isShow = true
        this.DeviceModal.title = title
        this.DeviceModal.disabled = title === '设备详情'
        this.$refs['device'].validate()
        this.$refs['device'].validate()
      },
      doDevice() {
        this.$refs['device'].validate((valid) => {
          if (valid) {
            this.DeviceModal.isLoading = true
            if (this.DeviceModal.title === '新建设备') {
              addDevcie(this.Device).then(res => {
                this.$Message.success(res.data.message)
                this.DeviceModal.isLoading = false
                this.DeviceModal.isShow = false
                this.getlist()
              })
            } else if (this.DeviceModal.title === '修改设备') {
              updateDevcie(this.Device).then(res => {
                this.$Message.success(res.data.message)
                this.DeviceModal.isLoading = false
                this.DeviceModal.isShow = false
                this.getlist()
              })
            }
          }
        })
      }
    }
  }
</script>

<style>

</style>
