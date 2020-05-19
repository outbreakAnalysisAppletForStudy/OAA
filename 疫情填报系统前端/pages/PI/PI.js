// pages/PI/PI.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    IDNum : '',
    id: "123123123123",
    tel: "15555555555",
    area: "01-01-01-01-01",
    pos: "高新区xx小区x号x栋",
    perStatus: 0,
    isFinish:''
  }, 
  /*得到ID*/
  setIDNum:function(e){
    this.setData({
      IDNum:e.detail.value
    })
  },
  cx:function(e){
    if (this.data.IDNum == '') {
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
     * 下面是测试带参跳转
     
    wx.navigateTo({
      url: '../PI-res/PI-res?id=' + this.data.id + '&tel=' + this.data.tel + '&area=' + this.data.area + '&pos=' + this.data.pos + '&perStatus=' + this.data.perStatus

    })
    */
    var that =this
    wx.request({
      url: 'http://localhost:8080/Client/getPersonInfo',
      method: 'POST',
      header:{
        'content-type':'application/x-www-form-urlencoded'
      },
      data: {
        'token': 'DEEPDARKFANTASY',
        'id': that.data.IDNum
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.isGet == 1) {
          that.setData({
            id: res.data.info.id,
            tel: res.data.info.tel,
            area: res.data.info.area,
            pos: res.data.info.pos,
            perStatus: res.data.info.perStatus
          })
          wx.navigateTo({
            url: '../PI-res/PI-res?id=' + that.data.id + '&tel=' + that.data.tel + '&area=' + that.data.area + '&pos=' + that.data.pos + '&perStatus=' + that.data.perStatus
          })
        }
        if (res.data.isGet == 0) {
          wx.showToast({
            title: '查询失败',
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