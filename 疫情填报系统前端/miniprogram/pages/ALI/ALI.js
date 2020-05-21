// pages/ALI/ALI.js\
const app=getApp()
const url = require('../../utils/url.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    level: 1,
    name: "北京市",
    total: 2345,
    recent: 123,
    exist: 1234,
    dead: 10,
    recover: 2,
    suspect: 12345
  },
  YQTB:function(){
    wx.navigateTo({
      url: '../ALI-YQTB/ALI-YQTB',
    })
  },
  ZDXJ: function () {
    if (app.globalData.adminInfo.level < 4){
      wx.navigateTo({
        url: '../ALI-ZDXJ/ALI-ZDXJ',
      })
    }else{
      wx.showToast({
        title: '您没有该权限',
        icon: 'fail',
        image: '/images/fail.png',
        duration: 2000
      })
   }
  },

  XQCX: function (e) {
    if (app.globalData.adminInfo.level==1){
    //if (this.data.level == 1) {
      wx.showToast({
        title: '您是最高管理员！',
        icon: 'loading',
        duration: 2000
      })
      wx.request({
        url:url.url.getNationInfo,
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        data: {
          "token": "DEEPDARKFANTASY"
        },
        success: function (res) {
          if (res.data.code == 0) {
            app.globalData.naInfo = res.data.info;
            app.globalData.dailyTextContent = res.data.dailyTextContent;
            app.globalData.incrData = res.data.incrData;
            wx.navigateTo({
              url: '../NI/NI'
            })
          }
          if (res.data.code == 404) {
            wx.showToast({
              title: '加载失败!',
              icon: 'fail',
              image: '/images/fail.png',
              duration: 2000
            })
          }
        }
      })
    }else{
    /**
     * 下面是带参跳转测试
     
    wx.navigateTo({
      url: '../ALI-XQCX/ALI-XQCX?level=' + this.data.level + '&name=' + this.data.name + '&total=' + this.data.total + '&recent=' + this.data.recent + '&exist=' + this.data.exist + '&dead=' + this.data.dead + '&recover=' + this.data.recover + '&suspect=' + this.data.suspect
    })
    */
    var that =this
    wx.request({
      url:url.url.getAreaInfo,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      data: {
        'sessionKey': app.globalData.sessionkey
      },
      success: function (res) {
        console.log(res.data)
        console.log(1)
        if (res.data.code == 0) {
          that.setData({
            level: res.data.level,
            name: res.data.detail.name,
            total: res.data.detail.total,
            recent: res.data.detail.recent,
            exist: res.data.detail.exist,
            dead: res.data.detail.dead,
            recover: res.data.detail.recover,
            suspect: res.data.detail.suspect,
          })
          wx.navigateTo({
            url: '../ALI-XQCX/ALI-XQCX?level=' + that.data.level + '&name=' + that.data.name + '&total=' + that.data.total + '&recent=' + that.data.recent + '&exist=' + that.data.exist + '&dead=' + that.data.dead + '&recover=' + that.data.recover + '&suspect=' + that.data.suspect
          })
        }
        if (res.data.code == 1) {
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