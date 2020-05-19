// pages/ALI-ZDXJ/ALI-ZDXJ.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:'',
    id:'',
    level:'',
    area:'',
    multiIndex: [0, 0, 0],
    region: [],
    region1: [],
    customItem: '全部',
    PhoneNum: '',
    isFinish:false,
    isFinish2:true
  },
  setname:function(e){
    this.setData({
      name:e.detail.value
    })
  },
  setIDNum: function (e) {
    this.setData({
      id: e.detail.value
    })
  },
  /*获取电话号码*/
  setPhoneNum: function (e) {
    this.setData({
      PhoneNum: e.detail.value
    })
  },
  fromsubmit:function(){
    if (this.data.name == '' | this.data.id == '' | this.data.PhoneNum == ''){
      this.setData({
        isFinish2:false
      })
    }else{
      this.setData({
        isFinish2: true
      })
    }
    if (this.data.isFinish == false | this.data.isFinish2==false){
      if (this.data.isFinish == false&&this.data.isFinish2 == false){
        wx.showToast({
          title: '请检查输入!',
          icon: 'fail',
          image: '/images/fail.png',
          duration: 2000
        })
      }
      if (this.data.isFinish == false && this.data.isFinish2 == true) {
        wx.showToast({
          title: '请检查地区选择!',
          icon: 'fail',
          image: '/images/fail.png',
          duration: 2000
        })
      }
      if (this.data.isFinish == true && this.data.isFinish2 == false) {
        wx.showToast({
          title: '输入不能为空!',
          icon: 'fail',
          image: '/images/fail.png',
          duration: 2000
        })
      }
    }else{
    wx.request({
      url: '',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      data: {
        'token': 'DEEPDARKFANTASY',
        'sessionKey': app.globalData.sessionkey,
        'name':this.data.name,
        'id': this.data.id,
        'area':this.data.region1,
        'tel':this.data.PhoneNum
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.code == 0) {
          wx.showToast({
            title: '提交成功！',
            icon: 'success',
            duration: 2000
          })
          wx.navigateTo({
            url: '../ALI/ALI'
          })
        }
        if (res.data.code == 1) {
          wx.showToast({
            title: '参数字段有误',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if (res.data.code == 2) {
          wx.showToast({
            title: '参数格式有误',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if (res.data.code == 404) {
          wx.showToast({
            title: 'sessionKey有误',
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
    this.setData({
      level:app.globalData.adminInfo.level,
      area: app.globalData.adminInfo.area,
      region: app.globalData.adminInfo.area,
      region1: app.globalData.adminInfo.area,
      //level:1,
     // area:['','',''],
      //region: ['', '', ''],
     // region1: ['', '', ''],
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  bindRegionChange: function (e) {
    console.log(e.detail.code)
    console.log(e.detail.value)
    this.setData({
      region: e.detail.value
    })
    if(this.data.level==1){
      this.setData({
        'region1[0]': this.data.region[0],
        'region1[1]': '',
        'region1[2]': ''
      })
      this.setData({
        isFinish: true
      })
    }
    if (this.data.level == 2) {
      this.setData({
        'region1[0]': this.data.area[0],
        'region1[1]': this.data.region[1],
        'region1[2]': ''
      })
      if(this.data.region[0]==this.data.area[0]){
        this.setData({
          isFinish:true
        })
      } else{
        wx.showToast({
          title: '选择无效！',
          icon: 'fail',
          image: '/images/fail.png',
          duration: 2000
        })
      }
    }
    if (this.data.level == 3) {
      this.setData({
        'region1[0]': this.data.area[0],
        'region1[1]': this.data.area[1],
        'region1[2]': this.data.region[2]
      })
      if (this.data.region[0] == this.data.area[0] && this.data.region[1] == this.data.area[1]) {
        this.setData({
          isFinish: true
        })
      } else {
        wx.showToast({
          title: '选择无效！',
          icon: "fail",
          image: '/images/fail.png',
          duration: 2000
        })
      }
    }
  },

})