// pages/NI-SJ/NI-SJ.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dailyTextContent:{},
    incrData: {},
    confirmedIncr: '',
    suspectedIncr: '',
    deadIncr: '',
    curedIncr: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      dailyTextContent: app.globalData.dailyTextContent,
      incrData: app.globalData.incrData
    })
    if (this.data.incrData.confirmedIncr>=0){
      this.setData({
        confirmedIncr: '+' + String(this.data.incrData.confirmedIncr)
      })
    } else{
      this.setData({
        confirmedIncr: String(this.data.incrData.confirmedIncr)
      })
    }
    if (this.data.incrData.suspectedIncr >= 0) {
      this.setData({
        suspectedIncr: '+' + String(this.data.incrData.suspectedIncr)
      })
    } else {
      this.setData({
        suspectedIncr: String(this.data.incrData.suspectedIncr)
      })
    }
    if (this.data.incrData.deadIncr >= 0) {
      this.setData({
        deadIncr: '+' + String(this.data.incrData.deadIncr)
      })
    } else {
      this.setData({
        deadIncr: String(this.data.incrData.deadIncr)
      })
    }
    if (this.data.incrData.curedIncr >= 0) {
      this.setData({
        curedIncr: '+' + String(this.data.incrData.curedIncr)
      })
    } else {
      this.setData({
        curedIncr: String(this.data.incrData.curedIncr)
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
    app.editTabBar();    //显示自定义的底部导航
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