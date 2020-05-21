// pages/NI-ZX/NI-ZX.js
// 地图 svg数据
import geoJson from "../../utils/mapData.js";
// api 文件
import echarts from "../../ec-canvas/echarts.js";
var app = getApp()
// 定义全局图像实例名
let chart = null
let myMap = null
Page({

  /**
   * 页面的初始数据
   */
  data: {
    ecmap:{
      onInit:initChartMap
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

  },
})


function initChartMap(canvas, width, height) {
  let myMap = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(myMap);
  echarts.registerMap('china', geoJson); // 绘制中国地图
  const option = {
    tooltip: {
      trigger: 'item',
      backgroundColor: "#FFF",
      padding: [
        10,  // 上
        15,  // 右
        8,   // 下
        15,  // 左
      ],
      extraCssText: 'box-shadow: 2px 2px 10px rgba(21, 126, 245, 0.35);',
      textStyle: {
        fontFamily: "'Microsoft YaHei', Arial, 'Avenir', Helvetica, sans-serif",
        color: '#005dff',
        fontSize: 12,
      },
      formatter: `{b} :  {c}确诊`
    },
    visualMap: {
      // 左下角定义 在选中范围中的视觉元素 渐变地区颜色
      type: 'piecewise', // 类型为分段型
      top: "bottom",
      right: 10,
      splitNumber: 6,
      seriesIndex: [0],
      itemWidth: 20, // 每个图元的宽度
      itemGap: 2,    // 每两个图元之间的间隔距离，单位为px
      pieces: [      // 自定义每一段的范围，以及每一段的文字
        { gte: 10000, label: '10000人以上'}, // 不指定 max，表示 max 为无限大（Infinity）。
        { gte: 1000, lte: 9999, label: '1000-9999人'},
        { gte: 500, lte: 999, label: '500-999人'},
        { gte: 100, lte: 499, label: '100-499人'},
        { gte: 10, lte: 99, label: '10-99人'},
        { gte: 1, lte: 9, label: '1-9人'} ,         // 不指定 min，表示 min 为无限大（-Infinity）。
        {lte:0,label:'注：港澳台未统计'}
      ],
      color: [
        "#ffffff",
        "#ffe5db",
        "#ff9985",
        "#f57567",
        "#e64546",
        "#b80909",
      ].reverse(),
      textStyle: {
        color: '#737373'
      }
    },
    geo: [
      {
        // 地理坐标系组件
        map: "china",
        roam: false,      // 可以缩放和平移
        aspectScale: 0.8, // 比例
        layoutCenter: ["50%", "38%"], // position位置
        layoutSize: 360,              // 地图大小，保证了不超过 360x360 的区域
        label: {
          // 图形上的文本标签
          normal: {
            show: true,
            textStyle: {
              color: "rgba(0, 0, 0, 0.9)",
              fontSize: '8'
            }
          },
          emphasis: { // 高亮时样式
            color: "#333"
          }
        },
        itemStyle: {
          // 图形上的地图区域
          normal: {
            borderColor: "rgba(0,0,0,0.2)",
            areaColor: "#005dff"
          },
          emphasis: {
            // 高亮时
            areaColor: "#c7fffd",
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowBlur: 10,
            borderWidth: 0,
            shadowColor: "rgba(0, 93, 255, 0.2)"
          }
        }
      }
    ],
    series: [
      {
        type: 'map',
        mapType: 'china',
        geoIndex: 0,
        roam: false, // 鼠标是否可以缩放
        label: {
          normal: {
            show: true
          },
          emphasis: {
            show: true
          }
        },
        data: [
          { name: '北京', value: app.globalData.naInfo[0].detail.total},
          { name: '天津', value: app.globalData.naInfo[1].detail.total },
          { name: '河北', value: app.globalData.naInfo[2].detail.total},
          { name: '山西', value: app.globalData.naInfo[3].detail.total },
          { name: '内蒙古', value: app.globalData.naInfo[4].detail.total },
          { name: '辽宁', value: app.globalData.naInfo[5].detail.total },
          { name: '吉林', value: app.globalData.naInfo[6].detail.total },
          { name: '黑龙江', value: app.globalData.naInfo[7].detail.total },
          { name: '上海', value: app.globalData.naInfo[8].detail.total },
          { name: '江苏', value: app.globalData.naInfo[9].detail.total },
          { name: '浙江', value: app.globalData.naInfo[10].detail.total },
          { name: '安徽', value: app.globalData.naInfo[11].detail.total },
          { name: '福建', value: app.globalData.naInfo[12].detail.total },
          { name: '江西', value: app.globalData.naInfo[13].detail.total },
          { name: '山东', value: app.globalData.naInfo[14].detail.total },
          { name: '河南', value: app.globalData.naInfo[15].detail.total },
          { name: '湖北', value: app.globalData.naInfo[16].detail.total },
          { name: '湖南', value: app.globalData.naInfo[17].detail.total },
          { name: '广东', value: app.globalData.naInfo[18].detail.total },
          { name: '广西', value: app.globalData.naInfo[19].detail.total },
          { name: '海南', value: app.globalData.naInfo[20].detail.total },
          { name: '重庆', value: app.globalData.naInfo[21].detail.total },
          { name: '四川', value: app.globalData.naInfo[22].detail.total },
          { name: '贵州', value: app.globalData.naInfo[23].detail.total },
          { name: '云南', value: app.globalData.naInfo[24].detail.total },
          { name: '西藏', value: app.globalData.naInfo[25].detail.total },
          { name: '陕西', value: app.globalData.naInfo[26].detail.total },
          { name: '甘肃', value: app.globalData.naInfo[27].detail.total },
          { name: '青海', value: app.globalData.naInfo[28].detail.total },
          { name: '宁夏', value: app.globalData.naInfo[29].detail.total },
          { name: '新疆', value: app.globalData.naInfo[30].detail.total },
          { name: '香港', value: 0 },
          { name: '澳门', value: 0 },
          { name: '台湾', value: 0 },
          { name: '南海诸岛', value: 1 },
        ]
      }]
  };

  myMap.setOption(option);
  return myMap
}