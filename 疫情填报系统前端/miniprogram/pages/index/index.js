//index.js
//获取应用实例
const app = getApp()
const url = require('../../utils/url.js')
Page({
  data: {
    motto: 'Hello World',
    load:true,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    info: [
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "01", "detail": { "name": "北京市", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "02", "detail": { "name": "河北省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "03", "detail": { "name": "辽宁省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
      { "province": "04", "detail": { "name": "吉林省", "total": 2345, "recent": 123, "exist": 1234, "dead": 10, "recover": 2, "suspect": 12345 } },
    ],
    dailyTextContent: {
      confirmedCount: 84458,
      suspectedCount: 4,
      deadCount: 4644,
      curedCount: 79600
    },
    incrData: {
      confirmedIncr: 8,
      suspectedIncr: 1,
      deadIncr: 0,
      curedIncr: 28
    }
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  AdLogIn:function(){
    wx.navigateTo({
      url: '../login/login'
    })
  },
  PersonalInformation:function(){
    wx.navigateTo({
      url: '../PI/PI'
    })
  },
  fff:function(){
      wx.showToast({
        title: '加载中',
        icon: 'loading',
        duration: 10000
      })
  },
  NationInformation:function(){
    /**
     * 下面测试跳转，请删掉
    
    wx.navigateTo({
      url: '../NI/NI'
    })
    app.globalData.naInfo=this.data.info;
    app.globalData.dailyTextContent = this.data.dailyTextContent;
    app.globalData.incrData = this.data.incrData;
    */

    //console.log(123)
    var that =this
    wx.request({
      url: url.url.getNationInfo,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      data:{
        "token": "DEEPDARKFANTASY"
      },
      success: function (res) {
        if (res.data.code == 0) {
          app.globalData.naInfo=res.data.info;
          app.globalData.dailyTextContent = res.data.dailyTextContent;
          app.globalData.incrData = res.data.incrData;
          wx.navigateTo({
            url: '../NI/NI'
          })
        }
        if (res.data.code == 404){
          wx.showToast({
            title: '加载失败!',
            icon: 'fail',
            image: '/images/fail.png',
            duration: 2000
          })
        }
      },fail:function(res){
        console.log('请求失败')
        console.log(res)
      }
    })
  },
  onLoad: function () {
    // 初始化
    wx.cloud.init()
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
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
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
