// pages/log/log.js
const app = getApp()
const url = require('../../utils/url.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    IDNum:'',
    PhoneNum:'',
    data:0,
    isFinish:true,
    nickName:''
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  /*获取身份证号*/ 
  setIDNum:function(e){
    this.setData({
      IDNum:e.detail.value
    })
  },
  /*获取电话号码*/
  setPhoneNum:function(e){
    this.setData({
      PhoneNum:e.detail.value
    })
  },
  login:function(e){
    //console.log(url.url.login)
    this.setData({
      nickName: this.data.userInfo.nickName
    })
    if (this.data.IDNum == '' | this.data.PhoneNum == '' | this.data.nickName == '') {
      this.setData({
        isFinish: false
      })
    } else {
      this.setData({
        isFinish: true
      })
    }
    if (this.data.isFinish == false) {
      wx.showToast({
        title: '输入不能为空!',
        icon: 'fail',
        image: '/images/fail.png',
        duration: 2000
      })
    } else {
    /**
     * 下面是测试跳转
     
    wx.navigateTo({
      url: '../ALI/ALI'
      //url: '../bind/bind'
    })
    */
    wx.request({
      url: url.url.login,
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        'token':'DEEPDARKFANTASY',
        'wxid': this.data.userInfo.nickName,
        'id': this.data.IDNum,
        'tel': this.data.PhoneNum
      },
      success:function(res){
        //console.log(res.data)
        app.globalData.sessionkey = res.data.session
        if(res.data.code==0){
          app.globalData.adminInfo = res.data.adminInfo
          wx.showToast({
            title: '登陆成功!',
            icon: 'success',
            duration: 2000
          }),
          wx.navigateTo({
            url: '../ALI/ALI'
          })
        }
        if (res.data.code==1){
          wx.showToast({
            title: '验证失败！',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if (res.data.code == 2) {
          wx.showToast({
            title: '参数错误！',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
        if(res.data.code==3){
          wx.showToast({
            title: '请先进行绑定',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
          wx.navigateTo({
            url: '../bind/bind'
          }) 
        }
      }
    })
    }
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

  }
})
