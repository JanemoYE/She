<template>
	<view class="content">
		
		<view class="toolTipBg" @click="delToolTip()" v-if="index==1">
			<view class="toolTip">
				<view class="Title">
					检测到新版本
				</view>
				<view class="contentFont">
					是否前往下载？
				</view>
				<view class="btn" @click="copyUrl()">
					复制下载链接
				</view>
			</view>
		</view>
		
		<view class="article" :style="'background: url('+articleData[2]+');'">
			<image :src="articleData[2]" mode="widthFix"></image>
		</view>
		<view style="margin-bottom: -450rpx;margin-top: -450rpx;text-align: center;opacity: 0.95;">
			<image style="border-radius: 40rpx;width: 650rpx;" :src="articleData[2]" mode="widthFix"></image>
		</view>


		<view class="articleFont">
			{{articleData[3]}}
		</view>

		<view class="comment">
			<view class="myFiery">
				<view class="images">
					<image :src="userArrays[2]" mode="widthFix"></image>
				</view>
				<view class="name">
					{{userArrays[1]}}
				</view>
			</view>
			<view class="input" style="margin-left: 50rpx;">
				<input type="text" @input="userContentInput($event)" :value="userContent" placeholder="请留下您的评论..."
					style="font-size: 26rpx;" />
			</view>
			<view class="sumbit" @click="addComment()">
				发布
			</view>
		</view>

		<view class="button">
			<view @click="backArticle()">
				<image src="../../static/Index/back.png" mode="widthFix"></image>
				<!--点击上一个-->
			</view>
			<view>
				<view class="author" @click="comment()">
					{{articleData[4]}}
				</view>
			</view>
			<view @click="nextArticle()">
				<image src="../../static/Index/next.png" mode="widthFix"></image>
				<!--点击下一个-->
			</view>
		</view>
		<view style="font-size: 24rpx;color: gray;margin-left: 80rpx;margin-top: 5rpx;">
			点击作者名称，进入评论区...
		</view>
		
		<view style="height: 250rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userArrays: {},
				articleData: {},
				nowIndex: 0,
				userContent: "",
				time: "",
				allArticleId: [],
				version:"",
				newVersion:"",
				url:"http://124.220.174.134:8888/down/2FUDXH9TxNAr",
				index:0
			}
		},
		onShow() {
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;
			//获取所有文章的id
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/allArticleId",
				success: function(res) {
					if (res.data.code == 0) {
						for (var i = 0; i < res.data.data.length; i++) {
							that.allArticleId[i] = res.data.data[i].id
						}
						uni.request({
							data: {
								id: that.allArticleId[that.nowIndex]
							},
							method: "GET",
							url: getApp().globalData.BaseUrl + "/Article/content",
							success: function(res) {
								if (res.data.code == 0) {
									that.articleData = res.data.data
								} else {
									uni.showToast({
										title: '好像什么都没查到呢...',
										icon: 'none',
										duration: 2000
									});
								}

							}
						})
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}

				}
			})
			uni.hideLoading();
		},
		onPullDownRefresh() {
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			} else {
				var that = this;
				this.nowIndex = parseInt(Math.random()*this.allArticleId.length)
				this.userArrays = getApp().globalData.userArrays
				//获取所有文章的id
				uni.request({
					data: {},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Article/allArticleId",
					success: function(res) {
						if (res.data.code == 0) {
							for (var i = 0; i < res.data.data.length; i++) {
								that.allArticleId[i] = res.data.data[i].id
							}
							uni.request({
								data: {
									id: that.allArticleId[that.nowIndex]
								},
								method: "GET",
								url: getApp().globalData.BaseUrl + "/Article/content",
								success: function(res) {
									if (res.data.code == 0) {
										that.articleData = res.data.data
									} else {
										uni.showToast({
											title: '好像什么都没查到呢...',
											icon: 'none',
											duration: 2000
										});
									}

								}
							})
						} else {
							uni.showToast({
								title: '好像什么都没查到呢...',
								icon: 'none',
								duration: 2000
							});
						}

					}
				})
				uni.stopPullDownRefresh();
			}
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...'
			});
			uni.getStorage({
			    key:"userKey",
			    success: function(res){
			        getApp().globalData.userKey=JSON.parse(res.data)
			    }
			})
			uni.getStorage({
			    key:"userArrays",
			    success: function(res){
			        getApp().globalData.userArrays=JSON.parse(res.data)
			    }
			})

			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			} else {
				var that = this;
				this.userArrays = getApp().globalData.userArrays
				this.version=getApp().globalData.version;
				uni.request({
					data: {},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/Version/VersionInformation",
					success: function(res) {
						// console.log(res.data.data)
						that.newVersion=res.data.data
						if(that.version==that.newVersion){
							
						}else{
							// Plus.runtime.openURL("http://aboutlive.top/AboutLive.apk");
							// plus.runtime.openURL("http://baidu.com")
							that.index=1;
						}
					}
				})
				//获取所有文章的id
				uni.request({
					data: {},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Article/allArticleId",
					success: function(res) {
						if (res.data.code == 0) {
							for (var i = 0; i < res.data.data.length; i++) {
								that.allArticleId[i] = res.data.data[i].id
							}
							that.nowIndex=parseInt(Math.random()*res.data.data.length)
							uni.request({
								data: {
									id: that.allArticleId[that.nowIndex]
								},
								method: "GET",
								url: getApp().globalData.BaseUrl + "/Article/content",
								success: function(res) {
									if (res.data.code == 0) {
										that.articleData = res.data.data
									} else {
										uni.showToast({
											title: '好像什么都没查到呢...',
											icon: 'none',
											duration: 2000
										});
									}

								}
							})
						} else {
							uni.showToast({
								title: '好像什么都没查到呢...',
								icon: 'none',
								duration: 2000
							});
						}

					}
				})

			}
			uni.hideLoading();
		},
		methods: {
			delToolTip(){
				if(this.index==1){
					this.index=0;
				}
			},
			copyUrl(){
				uni.setClipboardData({ data: this.url });
			},
			userContentInput(value) {
				this.userContent = value.detail.value;
			},
			comment() {
				uni.navigateTo({
					url: "../comment/comment?id=" + this.articleData[0]
				});
			},
			backArticle() {
				var that = this;
				if (this.nowIndex == 0) {
					uni.showToast({
						title: '没有找到上一张的图片哦！',
						icon: 'none',
						duration: 2000
					});
				} else {
					this.nowIndex--;
					uni.request({
						data: {
							id: that.allArticleId[that.nowIndex]
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Article/content",
						success: function(res) {
							if (res.data.code == 0) {
								that.articleData = res.data.data
							} else {
								uni.showToast({
									title: '好像什么都没查到呢...',
									icon: 'none',
									duration: 2000
								});
							}

						}
					})
				}
			},
			nextArticle() {
				var that = this;
				this.nowIndex++;
				uni.request({
					data: {
						id: that.allArticleId[that.nowIndex]
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Article/content",
					success: function(res) {
						if (res.data.code == 0) {
							that.articleData = res.data.data
						} else {
							that.nowIndex--;
							uni.showToast({
								title: '好像什么都没查到呢...',
								icon: 'none',
								duration: 2000
							});
						}

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
			// 评论
			addComment() {
				this.nowTime()
				var that = this;
				if (this.userContent == "") {
					uni.showToast({
						title: '请填写评论内容',
						icon: 'none',
						duration: 2000
					});
				} else {
					uni.request({
						data: {
							articleId: that.allArticleId[that.nowIndex],
							userKey: getApp().globalData.userKey,
							userContent: that.userContent,
							time: that.time
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Article/addComment",
						success: function(res) {
							if (res.data.code == 0) {
								uni.showToast({
									title: '评论成功！',
									icon: 'none',
									duration: 3000
								});
								that.userContent = ""
							} else {
								that.nowIndex--;
								uni.showToast({
									title: '评论失败啦！',
									icon: 'none',
									duration: 2000
								});
							}

						}
					})
				}
			}
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.content {}

	.article {
		width: 100%;
		height: 500rpx;
		margin-top: 50rpx;
		border-radius: 50rpx;
		filter: blur(10px);
	}

	.articleFont {
		width: 85%;
		margin: 0 auto;
		margin-top: 650rpx;
		font-size: 32rpx; 
	}

	.author {
		text-align: center;
		color: #fff;
		margin-top: 14rpx;
		font-size: 32rpx;
	}

	.button {
		background-color: #2C2C2C;
		width: 80%;
		height: 70rpx;
		padding: 15rpx;
		border-radius: 60rpx;
		margin-left: 8%;
		display: flex;
		justify-content: space-between;
	}

	.button image {
		width: 70rpx;
	}

	.comment {
		margin-bottom: 80rpx;
		margin-top: 50rpx;
	}

	.myFiery {
		display: flex;
	}

	.myFiery .images image {
		width: 80rpx;
		border-radius: 50%;
		margin-left: 50rpx;
	}

	.myFiery .name {
		margin-top: 15rpx;
		margin-left: 15rpx;
	}

	.comment .sumbit {
		background-color: #2C2C2C;
		margin-left: 50rpx;
		color: #fff;
		width: 100rpx;
		text-align: center;
		padding: 12rpx;
		border-radius: 10rpx;
		font-size: 26rpx;
		margin-top: 20rpx;
		margin-left: 550rpx;
	}
	.toolTipBg{
		z-index: 3;
		position: fixed;
		top: 0;
		height: 1500rpx;
		width: 100%;
	}
	.toolTip{
		padding: 20rpx;
		position: fixed;
		background-color: #212121;
		width: 400rpx;
		height: 300rpx;
		border-radius: 35rpx;
		border: gray solid 2px;
		top: 300rpx;
		left: 150rpx;
	}
	.toolTip .Title{
		z-index: 2;
		text-align: center;
		font-size: 32rpx;
		margin-top: 10rpx;
		color: #fff;
	}
	.contentFont{
		color: #fff;
		text-align: center;
		margin-top: 20rpx;
		font-size: 24rpx;
	}
	.btn{
		background-color: #fff;
		width: 200rpx;
		padding: 30rpx;
		margin: 0 auto;
		text-align: center;
		margin-top: 50rpx;
		border-radius: 20rpx;
	}
</style>
