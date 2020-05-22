const app = getApp()
const url = require('../../utils/url.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    IDNum: '',
    PhoneNum: '',
    Pos: '',
    PerStatus: '',
    multiIndex: [0, 0, 0],
    region: ['四川省', '成都市', '郫都区'],
    customItem: '全部',
    ifFinish:true
  },
  /*获取身份证号*/
  setIDNum: function (e) {
    this.setData({
      IDNum: e.detail.value
    })
  },
  /*获取电话号码*/
  setPhoneNum: function (e) {
    this.setData({
      PhoneNum: e.detail.value
    })
  },
  /*获取地址*/
  setPos: function (e) {
    this.setData({
      Pos: e.detail.value
    })
  },
  setPerStatus: function (e) {
    this.setData({
      PerStatus: e.detail.value
    })
  },
  fromsubmit: function (e) {
    if (this.data.IDNum == '' | this.data.PhoneNum == '' | this.data.Pos == '' | this.data.PerStatus=='') {
      this.setData({
        isFinish: false
      })
    } else {
      this.setData({
        isFinish: true
      })
    } 
    if(this.data.isFinish==false){
      wx.showToast({
        title: '输入不能为空!',
        icon: 'fail',
        image: '/images/fail.png',
        duration: 2000
      })
    }else {
    wx.request({
      url: url.url.report,
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        'token': 'DEEPDARKFANTASY',
        'sessionKey': app.globalData.sessionkey,
        'id': this.data.IDNum,
        'tel': this.data.PhoneNum,
        "area": this.data.region,
        'pos': this.data.Pos,
        'perStatus': this.data.PerStatus
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.code == 0) {
          wx.showToast({
            title: '提交成功！',
            icon: 'success',
            duration: 2000
          }),
            wx.navigateBack({
              delta: 1, // 回退前 delta(默认为1) 页面 
              success: function (res) {
                // success 
              },
              fail: function () {
                // fail 
              },
              complete: function () {
                // complete 
              }
            }) 
        }
        if (res.data.code == 1) {
          wx.showToast({
            title: '参数个数和字段有误',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if (res.data.code == 2) {
          wx.showToast({
            title: '参数内容有误',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if (res.data.code == 404) {
          wx.showToast({
            title: 'session有误',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
      }
    })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  bindRegionChange: function (e) {
    console.log(e.detail.code)
    console.log(e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  
})