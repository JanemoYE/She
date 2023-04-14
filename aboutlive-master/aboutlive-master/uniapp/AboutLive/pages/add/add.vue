<template>
	<view>
		<view class="titleTop">
			<!--标题-->
			<view style="width: 50rpx;">

			</view>
			<view class="title">发布句子</view>
			<view style="width: 50rpx;">

			</view>
		</view>

		<view class="image" v-if="path==''" @click="loadImages()">
			<!--图片选择-->
			点击选择图片
		</view>
		<view class="imageBase64" v-else @click="loadImages()">
			<image :src="path" mode="widthFix"></image>
		</view>

		<view class="input">
			<!--内容输入-->
			<textarea @input="contentInput($event)" style="height: 300rpx;width: 100%;font-size: 26rpx;" type="text"
				:value="content" placeholder="输入内容..." />
		</view>
		<view class="fontNumber">
			{{fontNumber}}/140
		</view>

		<view class="author">
			<view class="images">
				<image src="../../static/add/peopleIcon.png" mode="widthFix"></image>
			</view>
			<view class="input1">
				<input type="text" :value="authorName" style="font-size: 26rpx;" placeholder="填写作者名..."
					@input="authorNameInput($event)" />
			</view>
		</view>

		<view class="button">
			<view class="btn1"
				:style="btn==1 ? 'background-color: rgb(0,0,0);color:#fff;':'background-color: rgba(0,0,0,0.06);'"
				@click="btnClick(1)">摘自</view>
			<view class="btn2"
				:style="btn==2 ? 'background-color: rgb(0,0,0);color:#fff;':'background-color: rgba(0,0,0,0.06);'"
				@click="btnClick(2)">原创</view>
		</view>

		<view style="font-size: 28rpx;color: gray;margin: 30rpx;">
			分区:
		</view>
		<view class="typeList">
			<view :class="type==0? 'ol':'oln'" @click="typeChoose(0)">emo</view>
			<view :class="type==1? 'ol':'oln'" @click="typeChoose(1)">阳光</view>
			<view :class="type==2? 'ol':'oln'" @click="typeChoose(2)">动漫</view>
			<view :class="type==3? 'ol':'oln'" @click="typeChoose(3)">朋友圈文案</view>
			<view :class="type==4? 'ol':'oln'" @click="typeChoose(4)">英文</view>
			<view :class="type==5? 'ol':'oln'" @click="typeChoose(5)">影视</view>
			<view :class="type==6? 'ol':'oln'" @click="typeChoose(6)">作文</view>
			<view :class="type==7? 'ol':'oln'" @click="typeChoose(7)">外语</view>
			<view :class="type==8? 'ol':'oln'" @click="typeChoose(8)">爱情</view>
		</view>

		<view class="submit" @click="submit()">
			提交
		</view>
	</view>
</template>

<script>
	import {
		pathToBase64,
		base64ToPath
	} from '../../js_sdk/mmmm-image-tools/index.js'
	export default {
		data() {
			return {
				path: "",
				type: 0,
				imagesBase64: "", //图片的base64编码
				content: "", //文章内容
				fontNumber: 0, //文章字数
				btn: 1,
				time: "", //当前时间
				authorName: "" //作者名字
			}
		},
		onLoad() {
			uni.getStorage({
				key: "userKey",
				success: function(res) {
					getApp().globalData.userKey = JSON.parse(res.data)
				}
			})
			uni.getStorage({
				key: "userArrays",
				success: function(res) {
					getApp().globalData.userArrays = JSON.parse(res.data)
				}
			})
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			}
		},
		methods: {
			typeChoose(type) {
				this.type = type
			},
			contentInput(e) {
				this.content = e.detail.value
				this.fontNumber = this.content.length
				if (this.content.length == 140) {
					uni.showToast({
						title: '输入已达上限！',
						icon: 'none',
						duration: 2000
					});
				}
			},
			
			loadImages() {
				// 先拿到临时路径
				var that = this;
				uni.chooseImage({
					count: 1, //上传图片的数量，默认是9
					sizeType: ['compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: function(res) {
						that.path = res.tempFilePaths[0];
						console.log(that.path)
						// that.urlTobase64(that.path)

						pathToBase64(res.tempFilePaths[0])
							.then(base64 => {
								console.log(base64)
								that.imagesBase64 = base64
							}).catch(error => {
								console.error(error)
							})

					}
				});

			},
			
			btnClick(e) {
				this.btn = e;
			},

			authorNameInput(value) {
				this.authorName = value.detail.value
			},

			// 转换成base64 仅支持微信小程序
			urlTobase64(url) {
				console.log("urlTobase64成功调用")
				var that = this;
				uni.request({
					url: url,
					method: 'GET',
					responseType: 'arraybuffevarr',
					success: ress => {
						var base64 = wx.arrayBufferToBase64(ress.data); //把arraybuffer转成base64 
						base64 = "data:image/jpeg;base64," + base64 //不加上这串字符，在页面无法显示的哦
						that.imagesBase64 = base64;
						console.log(base64)
					}
				})

			},


			// 获取当前日期与时间
			nowTime() {
				var date = new Date(); //Fri Oct 29 2021 16:37:56 GMT+0800 (CST)
				var timestamp = new Date().getTime(); //1635496676223		(毫秒级)
				// var y= date.getFullYear(); //获取完整的年份(4位)
				// var m= date.getMonth(); //获取当前月份(0-11,0代表1月)
				// var d= date.getDate(); //获取当前日(1-31)
				// var w= date.getDay(); //获取当前星期X(0-6,0代表星期天)
				// var h= date.getHours(); //获取当前小时数(0-23)
				// var i= date.getMinutes(); //获取当前分钟数(0-59)
				// var s= date.getSeconds(); //获取当前秒数(0-59)
				this.time = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date
					.getHours() + ":" + date.getMinutes();
			},

			submit() {

				uni.showLoading({
					title: '加载中...'
				});
				var that=this;
				uni.request({
					data: {
						id:3,
						keyWord:this.content
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Version/keyWords",
					success: function(res) {
						if(res.data.data==false){
							uni.hideLoading()
							uni.showToast({
								title: '内容中有敏感内容！',
								icon: 'none',
								duration: 3000
							});
						}else{
							
							if (that.imagesBase64 == "" || that.content == "" || that.authorName == "") {
								uni.showModal({
									title: '提示',
									content: '图片与内容，以及作者名称请勿留空！',
									success: function(res) {
										// if (res.confirm) {
										// 	console.log('用户点击确定');
										// } else if (res.cancel) {
										// 	console.log('用户点击取消');
										// }
									}
								});
							} else {
								that.nowTime();
								var that = this;
								uni.request({
									data: {
										userKey: getApp().globalData.userKey,
										imagesBase64: that.imagesBase64,
										content: that.content,
										author: that.authorName,
										from: that.btn,
										userContent: that.userContent,
										time: that.time,
										type: getApp().globalData.articleTypeList[that.type],
										featured: 0
									},
									header: {
										'Content-Type': 'application/x-www-form-urlencoded'
									},
									method: "POST",
									url: getApp().globalData.BaseUrl + "/Article/addArticle",
									success: function(res) {
										if (res.data.code == 0) {
											uni.hideLoading()
											uni.showToast({
												title: '发布成功！',
												icon: 'none',
												duration: 3000
											});
											that.userContent = ""
											//初始化变量
											that.path = "",
												that.type = 0,
												that.imagesBase64 = "", //图片的base64编码
												that.content = "", //文章内容
												that.fontNumber = 0, //文章字数
												that.btn = 1,
												that.time = "", //当前时间
												that.authorName = "" //作者名字
										} else {
											that.nowIndex--;
											uni.showToast({
												title: '发布失败啦！',
												icon: 'none',
												duration: 2000
											});
										}
									}
								})
							
							}
							
						}
					}
				})
				

			}
		}
	}
</script>

<style>
	page {
		font-size: 26rpx;
	}

	.titleTop {
		width: 95%;
		display: flex;
		margin: 0 auto;
		margin-top: 15rpx;
		justify-content: space-between;
	}

	.image {
		background-color: rgba(0, 0, 0, 0.05);
		color: gray;
		width: 90%;
		margin: 0 auto;
		margin-top: 40rpx;
		text-align: center;
		height: 500rpx;
		line-height: 500rpx;
		border-radius: 20rpx;
	}

	::-webkit-scrollbar {
		display: none;
	}

	.imageBase64 {
		width: 90%;
		margin: 0 auto;
		margin-top: 40rpx;
		height: 500rpx;
		overflow: hidden;
	}

	.imageBase64 image {
		width: 100%;
		border-radius: 20rpx;
	}

	.input {
		width: 90%;
		margin: 0 auto;
		margin-top: 20rpx;
		height: 300rpx;
	}

	.fontNumber {
		text-align: right;
		margin-right: 50rpx;
		color: gray;
	}

	.author {
		width: 95%;
		margin: 0 auto;
		margin-top: 40rpx;
		display: flex;
	}

	.author .images image {
		width: 80rpx;
	}

	.author .input1 {
		margin-left: 25rpx;
	}

	.author .input1 input {
		font-size: 26rpx;
		margin-top: 50rpx;
	}

	.button {
		margin-top: 30rpx;
		margin-left: 30rpx;
		display: flex;
	}

	.button .btn1 {
		background-color: rgba(0, 0, 0, 0.06);
		color: gray;
		padding: 10rpx 40rpx;
		border-radius: 40rpx;
		transition: 0.5s;
	}

	.button .btn2 {
		background-color: rgba(0, 0, 0, 0.06);
		color: gray;
		padding: 10rpx 40rpx;
		border-radius: 40rpx;
		margin-left: 50rpx;
		transition: 0.5s;
	}

	.submit {
		background-color: #000000;
		color: #fff;
		padding: 20rpx 40rpx;
		width: 300rpx;
		border-radius: 50rpx;
		text-align: center;
		margin: 0 auto;
		margin-top: 100rpx;
	}

	.typeList {
		overflow-x: auto;
		display: -webkit-box;
		-webkit-overflow-scrolling: touch;
		margin: 0rem 1rem
	}

	.typeList .ol {
		padding: 20rpx 40rpx;
		background-color: #212121;
		color: #fff;
		border-radius: 15rpx;
		margin-right: 20rpx;
	}

	.typeList .oln {
		padding: 20rpx 40rpx;
		background-color: #d3d3d3;
		color: #000;
		border-radius: 15rpx;
		margin-right: 20rpx;
	}
</style>
