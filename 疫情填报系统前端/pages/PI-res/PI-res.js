// pages/PI-res/PI-res.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: "",
    tel: '',
    area: '',
    pos: '',
    perStatus:'',
    PerStatus:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    /**
     * 赋值
     */
    this.setData({
      id : options.id,
      tel :options.tel,
      area : options.area,
      pos : options.pos,
      perStatus : options.perStatus  
    })
    if (this.data.perStatus == 0){
      this.setData({
        PerStatus:'疑似'
      })
    }
    if (this.data.perStatus == 1) {
      this.setData({
        PerStatus: '确诊'
      })
    }
    if (this.data.perStatus == 2) {
      this.setData({
        PerStatus: '痊愈'
      })
    }
    if (this.data.perStatus == 3) {
      this.setData({
        PerStatus: '死亡'
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