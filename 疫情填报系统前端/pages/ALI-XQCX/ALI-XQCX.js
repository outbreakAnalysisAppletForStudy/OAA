// pages/ALI-XQCX/ALI-XQCX.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    level: '',
    name: '',
    total: '',
    recent:'' ,
    exist:'' ,
    dead:'' ,
    recover:'' ,
    suspect: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      level: options.level,
      name: options.name,
      total: options.total,
      recent: options.recent,
      exist: options.exist,
      dead: options.dead,
      recover: options.recover,
      suspect: options.suspect
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

  }
})