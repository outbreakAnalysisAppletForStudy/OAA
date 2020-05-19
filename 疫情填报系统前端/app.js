//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    sessionkey:null,
    naInfo:null,
    dailyTextContent:null,
    incrDatanull:null,
    adminInfo:null,
  },

  //底部  
  editTabBar: function () {
    //使用getCurrentPages可以获取当前加载中所有的页面对象的一个数组，数组最后一个就是当前页面。
    var curPageArr = getCurrentPages();    //获取加载的页面
    var curPage = curPageArr[curPageArr.length - 1];    //获取当前页面的对象
    var pagePath = curPage.route;    //当前页面url
    if (pagePath.indexOf('/') != 0) {
      pagePath = '/' + pagePath;
    }
    var tabBar = this.globalData.tabBar;
    for (var i = 0; i < tabBar.list.length; i++) {
      tabBar.list[i].active = false;
      if (tabBar.list[i].pagePath == pagePath) {
        tabBar.list[i].active = true;    //根据页面地址设置当前页面状态    
      }
    }
    curPage.setData({
      tabBar: tabBar
    });
  },
  onReady() {
    const query = wx.createSelectorQuery()
    query.select('#myCanvas')
      .fields({ node: true, size: true })
      .exec((res) => {
        const canvas = res[0].node
        const ctx = canvas.getContext('2d')
        const dpr = wx.getSystemInfoSync().pixelRatio
        canvas.width = res[0].width * dpr
        canvas.height = res[0].height * dpr
        ctx.scale(dpr, dpr)

        ctx.fillRect(0, 0, 100, 100)
      })
  },
  globalData: {
    //底部导航栏显示
    tabBar: {
      "color": "#9E9E9E",
      "selectedColor": "#f00",
      "backgroundColor": "#fff",
      "borderStyle": "#ccc",
      "list": [
        {
          "pagePath": "/pages/NI/NI",
          "text": "全国总览",
          "iconPath": "/images/111.png",
          "selectedIconPath": "/images/111.png",
          "clas": "menu-item",
          "selectedColor": "#4EDF80",
          active: true
        },
        {
          "pagePath": "/pages/NI-ZX/NI-ZX",
          "text": "疫情地图",
          "iconPath": "/images/222.png",
          "selectedIconPath": "/images/222.png",
          "selectedColor": "#4EDF80",
          "clas": "menu-item",
          active: false
        },
        {
          "pagePath": "/pages/NI-SJ/NI-SJ",
          "text": "各省数据",
          "iconPath": "/images/333.png",
          "selectedIconPath": "/images/333.png",
          "selectedColor": "#4EDF80",
          "clas": "menu-item",
          active: false
        }
      ],
      "position": "bottom"
    },
  },
  onLaunch: function () {
    wx.BaaS = requirePlugin('sdkPlugin')
    // //让插件帮助完成登录、支付等功能
    wx.BaaS.wxExtend(wx.login,
      wx.getUserInfo,
      wx.requestPayment)
    wx.BaaS.init('6f9354d03f7f1b31a383')
  },
})