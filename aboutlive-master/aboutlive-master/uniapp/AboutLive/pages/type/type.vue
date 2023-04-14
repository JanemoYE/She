<template>
	<view>
		<view style="height: 50rpx;"></view>
		<view class="search">
			<view class="image" @click="search()">
				<image src="../../static/type/search.png" mode="widthFix"></image>
			</view>
			<view class="input">
				<input type="text" value="" placeholder="输入关键词..." @input="searchText($event)" />
			</view>
		</view>

		<view class="titleSwiper">
			<swiper circular autoplay interval=4000 style="height: 350rpx;">
				<swiper-item v-for="(item,index) in swipers" :key="index">
					<view @click="comment(item.id)">
						<image :src="item.imagesBase64" mode="widthFix" style="width: 100%;"></image>
					</view>
					<!-- <view class="text"><text>{{swipersText[index]}}</text></view> -->
				</swiper-item>
			</swiper>
		</view>

		<view class="list">
			<view class="li" @click="typeNav(1)">
				<view>
					<image src="../../static/type/rank.png" mode="widthFix"></image>
				</view>
				<view style="text-align: center;"><text>排行</text></view>
			</view>
			<view class="li" @click="typeNav(2)">
				<view>
					<image src="../../static/type/sad.png" mode="widthFix" style="width: 80rpx;"></image>
				</view>
				<view style="text-align: center;"><text>emo</text></view>
			</view>
			<view class="li" @click="typeNav(3)">
				<view>
					<image src="../../static/type/sun.png" mode="widthFix" style="width: 80rpx;"></image>
				</view>
				<view style="text-align: center;"><text>乐观</text></view>
			</view>
			<view class="li" @click="typeNav(4)">
				<view>
					<image src="../../static/type/dm.png" mode="widthFix" style="width: 80rpx;"></image>
				</view>
				<view style="text-align: center;"><text>动漫</text></view>
			</view>
			<view class="li" @click="typeNav(5)">
				<view>
					<image src="../../static/type/new.png" mode="widthFix" style="width: 80rpx;"></image>
				</view>
				<view style="text-align: center;"><text>最新</text></view>
			</view>
		</view>
		
		<view style="display: flex;" @click="topicNav(1)">
			<view class="language" style="display: flex;">
				<view class="image">
					<image src="../../static/type/language.png" mode="widthFix"></image>
				</view>
				<view style="margin-left: 30rpx;margin-top: 10rpx;">
					外语
				</view>
			</view>
			
			<view class="pyq">
				<view>
					<view style="font-size: 22rpx;margin-top: 5rpx;">
						朋友圈文案,没灵感？
					</view>
					<view style="color: gray;">
						图文 来看看！
					</view>
				</view>
				<view style="text-align: right;width: 150rpx;">
					<image src="../../static/type/pyq.png" mode="widthFix"></image>
				</view>
			</view>
		</view>

		<view class="type">
			<lgd-tab :tabValue="tabValue" :tabIndex="tabIndex" @getIndex="tIndexClick"/>
		</view>
		
		<view v-if="tabIndex==0">
			<view class="topicDay" @click="topicNav(1)">
				<view class="box">
					<view class="title">
						每日话题
					</view>
					<view class="content">
						"快来看看谁是话题终结者"
					</view>
					<view style="background-color: #F0AD4E;height: 2px;width: 80rpx;margin: 20rpx;margin-left: 0;"></view>
					<view style="color: #fff;">
						35W 人参与
					</view>
				</view>
				<view><image src="https://tse4-mm.cn.bing.net/th/id/OIP-C.DNu1nD6pmEK6IU8d-kTS5AHaHa?pid=ImgDet&rs=1" mode="widthFix"></image></view>
			</view>
			<view class="addTopic" @click="topicNav(2)">
				<view class="box">
					<view class="title">
						新建话题
					</view>
					<view class="content">
						"在这里发言"
					</view>
				</view>
				<view><image src="https://tse4-mm.cn.bing.net/th/id/OIP-C.DNu1nD6pmEK6IU8d-kTS5AHaHa?pid=ImgDet&rs=1" mode="widthFix"></image></view>
			</view>
			<view class="allTopic" @click="topicNav(3)">
				查看更多话题
			</view>
			
			
			<view style="height: 400rpx;"></view>
			
			<view class="recommend">
				<view  v-for="(item,index) in randTenUser" :key="index">
					<view v-if="item[4]=='false'" class="item">
						<view class="del">
							x
						</view>
						<view class="headimg" @click="userHome(item[3])">
							<image :src="item[0]" mode="widthFix"></image>
						</view>
						<view class="name">
							{{item[1]}}
						</view>
						<view style="font-weight: bold;text-align: center;font-size: 22rpx;">
							<view class="lv" :class="'Exe'+item[2]">
								<image src="../../static/lv.png" mode="widthFix"></image>
								<view>
									Lv-{{item[2]}}
								</view>
							</view>
						</view>
						<view class="focusBtn" @click="others(index)">
							关注
						</view>
					</view>
				</view>
			</view>
			
		</view>
		
		<view v-if="tabIndex==1">
			<view v-for="(item,index) in randTaken" :key="index">
				<view class="bg" @click="comment(item.id)">
					<view class="image">
						<image :src="item.imagesBase64" mode="widthFix"></image>
					</view>
					<view class="font">
						{{item.content}}
					</view>
					<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
						{{item.author}}
					</view>
				</view>
			</view>
		</view>
		
		<view v-if="tabIndex==2">
			<view v-for="(item,index) in randOriginal" :key="index">
				<view class="bg" @click="comment(item.id)">
					<view class="image">
						<image :src="item.imagesBase64" mode="widthFix"></image>
					</view>
					<view class="font">
						{{item.content}}
					</view>
					<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
						{{item.author}}
					</view>
				</view>
			</view>
		</view>
	
		<view v-if="tabIndex==3">
			<view v-for="(item,index) in randFeatured" :key="index">
				<view class="bg" @click="comment(item.id)">
					<view class="image">
						<image :src="item.imagesBase64" mode="widthFix"></image>
					</view>
					<view class="font">
						{{item.content}}
					</view>
					<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
						{{item.author}}
					</view>
				</view>
			</view>
		</view>
		<view style="height: 150rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchTextContent: "",
				swipers: [
					
				],
				swipersText: [
					"孤独感时常体现在一种矛盾上，就是你经常是处在一种挣扎的状态：既希望别人关注自己，又不知道该怎么去接触和回应别人，于是随着情绪和别人相处。",
					"即使慢，驰而不息，纵会落后，纵会失败，但一定可以达到他所向的目标。"
				],
				tabValue: ['为你推荐', '摘自',  '原创',  '精选'],
				tabIndex: 0,
				randFeatured:{},
				randTaken:{},
				randOriginal:{},
				randTenUser:{},
				othersUserKey:""
			}
		},
		onPullDownRefresh() {
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			}
			this.tabIndex=0;
			var that = this;
			//获取摘自文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randTaken",
				success: function(res) {
					if (res.data.code == 0) {
						that.randTaken=res.data.data
						// console.log(that.randTaken)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			//获取原创文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randOriginal",
				success: function(res) {
					if (res.data.code == 0) {
						that.randOriginal=res.data.data
						// console.log(that.randOriginal)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			// 获取精选文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randFeatured",
				success: function(res) {
					if (res.data.code == 0) {
						that.randFeatured=res.data.data
						// console.log(that.randFeatured)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			uni.stopPullDownRefresh()
		},
		onLoad() {
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
			}
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;
			
			//随机获取十个用户
			uni.request({
				data: {
					userKey:getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/User/randTenUser",
				success: function(res) {
					if (res.data.code == 0) {
						that.randTenUser=res.data.data
						console.log(that.randTenUser)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			//获取摘自文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randTaken",
				success: function(res) {
					if (res.data.code == 0) {
						that.randTaken=res.data.data
						// console.log(that.randTaken)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			
			//获取首页推广信息
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/featured",
				success: function(res) {
					if (res.data.code == 0) {
						that.swipers=res.data.data
						console.log(res.data.data)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			
			//获取原创文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randOriginal",
				success: function(res) {
					if (res.data.code == 0) {
						that.randOriginal=res.data.data
						// console.log(that.randOriginal)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})
			// 获取精选文章
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/randFeatured",
				success: function(res) {
					if (res.data.code == 0) {
						that.randFeatured=res.data.data
						// console.log(that.randFeatured)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
					uni.hideLoading();
				}
			})
			
		},
		methods: {
			topicNav(index){
				uni.showToast({
					title: '正在通宵开发中...',
					icon: 'none',
					duration: 3000
				});
				// if(index==1){
				// 	uni.navigateTo({
				// 		url:'../topic/topic'
				// 	})
				// }else if(index==2){
				// 	uni.navigateTo({
				// 		url:'../topic/addTopic/addTopic'
				// 	})
				// }else if(index==3){
				// 	uni.navigateTo({
				// 		url:'../topic/AllTopic/AllTopic'
				// 	})
				// }
			},
			userHome(key){
				uni.navigateTo({
					url: "../userHome/userHome?userKey=" +key
				});
			},
			others(index){
				// console.log(key)
				this.othersUserKey=this.randTenUser[index][3]
				this.addfoucs()
				this.randTenUser[index][4]="True"
			},
			searchText(event) {
				// console.log(event.detail.value)
				this.searchTextContent = event.detail.value
			},
			search() { //搜索
				if(this.searchTextContent==null || this.searchTextContent==""){
					uni.showToast({
						title: '查询为空！',
						icon: 'none',
						duration: 3000
					});
				}else{
					uni.navigateTo({
						url:'./searchContent/searchContent?text='+this.searchTextContent
					})
				}
			},
			tIndexClick(rel){
				this.tabIndex=rel
			},
			addfoucs() { //关注
				var that = this;
				if(that.othersUserKey!=getApp().globalData.userKey){
					uni.request({
						data: {
							othersUserKey: that.othersUserKey,
							myUserKey: getApp().globalData.userKey
						},
						method: "GET",
						url: getApp().globalData.BaseUrl+"/User/addFocus",
						success: function(res) {
							if (res.data.code == 0) {
								uni.showToast({
									title: '关注成功！',
									icon: 'none',
									duration: 3000
								});
								that.foucsTrue="true"
							} else {
								uni.showToast({
									title: '关注失败！',
									icon: 'none',
									duration: 2000
								});
							}
						}
					})
				}else{
					uni.showToast({
						title: '您不能关注自己！',
						icon: 'none',
						duration: 3000
					});
				}
			},
			delfoucs(){//取消关注
				var that = this;
				uni.request({
					data: {
						othersUserKey: that.othersUserKey,
						myUserKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/delFocus",
					success: function(res) {
						if (res.data.code == 0) {
							uni.showToast({
								title: '取关成功！',
								icon: 'none',
								duration: 3000
							});
							that.foucsTrue="false"
						} else {
							uni.showToast({
								title: '取关失败！',
								icon: 'none',
								duration: 2000
							});
						}
					}
				})
			},
			comment(id) {
				uni.navigateTo({
					url: "../comment/comment?id=" + id
				});
			},
			typeNav(i){
				if(i==1){
					uni.navigateTo({
						url:'rank/rank'
					})
				}else if(i==2){
					uni.navigateTo({
						url:'emo/emo?typeString=emo'
					})
				}else if(i==3){
					uni.navigateTo({
						url:'emo/emo?typeString=阳光'
					})
				}else if(i==4){
					uni.navigateTo({
						url:'emo/emo?typeString=动漫'
					})
				}else if(i==5){
					uni.navigateTo({
						url:'new/new'
					})
				}
			},
		}
	}
</script>

<style>
	input{
		font-size: 26rpx;
	}
	page{
		background-color: #f4f4f4;
		font-size: 26rpx;
	}
	.search {
		width: 90%;
		margin: 0 auto;
		display: flex;
		border: 1px solid #212121;
		border-radius: 50rpx;
		padding: 5rpx;
		margin-top: 30rpx;
	}

	.search .image image {
		width: 50rpx;
		margin-top: 10rpx;
		margin-left: 10rpx;
		margin-right: 10rpx;
	}

	.titleSwiper {
		width: 90%;
		margin: 0 auto;
		text-align: center;
		margin-top: 50rpx;
		border-radius: 30rpx;
		overflow: hidden;
	}

	.titleSwiper .text {
		position: absolute;
		top: 80rpx;

		/* left: 260rpx; */
		width: 95%;
		text-align: center;
		background-color: rgba(255, 255, 255, 0.7);
		padding: 25rpx 20rpx;
	}

	.search .input {
		margin-top: 15rpx;
		width: 80%;
	}

	.list {
		margin: 0 auto;
		display: flex;
		width: 90%;
		margin-top: 50rpx;
		justify-content: space-around;
	}

	.list .li image {
		width: 80rpx;
	}

	.type {
		width: 90%;
		margin: 0 auto;
		margin-top: 50rpx;
	}
	
	.bg{
		background-color: #fff;
		width: 90%;
		margin: 0 auto;
		margin-top: 40rpx;
		padding: 50rpx 20rpx;
		border-radius: 20rpx;
	}
	.bg .image{
		border-radius: 20rpx;
		overflow: hidden;
		text-align: center;
	}
	.bg .image image{
		border-radius: 20rpx;
		width: 80%;
	}
	.bg .font{
		color: #212121;
		line-height: 55rpx;
		font-size: 32rpx;
		width: 95%;
		margin: 0 auto;
	}
	.pyq{
		width: 400rpx;
		padding: 20rpx;
		background-color: #fff;
		display: flex;
		border-radius: 15rpx;
		margin-top: 20rpx;
		box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.22);
		margin-left: 20rpx;
	}
	.pyq image{
		width: 50rpx;
	}
	.language{
		background-color: #212121;
		color: #fff;
		font-size: 24rpx;
		box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.22);
		height: 50rpx;
		padding: 30rpx;
		margin-top: 20rpx;
		border-radius: 20rpx;
		margin-left: 50rpx;
	}
	.language image{
		width: 50rpx;
	}
	.topicDay{
		margin-top: 20rpx;
		position: absolute;
		width: 350rpx;
		height: 350rpx;
		border-radius: 30rpx;
		margin-left: 50rpx;
		overflow: hidden;
	}
	.topicDay image{
		width: 350rpx;
		position: absolute;
		margin-top: 0rpx;
		filter: blur(8rpx);
	}
	.box{
		position: absolute;z-index: 2;
		margin: 20rpx;
	}
	.box .title{
		background-color: #ffd334;
		width: 120rpx;
		color: #212121;
		padding: 10rpx;
		text-align: center;
		border-top-right-radius: 20rpx;
		border-top-left-radius: 20rpx;
		border-bottom-right-radius: 20rpx;
	}
	.box .content{
		color: #fff;
		margin-top: 40rpx;
		font-weight: bold;
		font-size: 40rpx;
	}
	
	.addTopic{
		margin-top: 20rpx;
		position: absolute;
		width: 300rpx;
		height: 250rpx;
		border-radius: 30rpx;
		margin-left: 420rpx;
		overflow: hidden;
	}
	.addTopic image{
		width: 300rpx;
		position: absolute;
		margin-top: 0rpx;
		filter: blur(8rpx);
	}
	.allTopic{
		background-color: #ffd334;
		width: 260rpx;
		text-align: center;
		padding: 20rpx;
		border-radius: 20rpx;
		color: #212121;
		position: absolute;
		left: 420rpx;
		margin-top: 300rpx;
		box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.22);
	}
	
	
	.focusBtn {
		background-color: #f28500cc;
		border-radius: 30rpx;
		width: 120rpx;
		height: 50rpx;
		text-align: center;
		color: #fff;
		line-height: 48rpx;
		font-size: 24rpx;
		margin-left: 20rpx;
		margin-top: 30rpx;
	}
	
	.del {
		font-family: '楷体';
		font-size: 28rpx;
		background-color: #ebebeb;
		height: 30rpx;
		width: 30rpx;
		position: relative;
		margin-top: -40rpx;
		line-height: 25rpx;
		border-radius: 50%;
		color: gray;
		text-align: center;
		margin-left: 160rpx;
	}
	
	::-webkit-scrollbar{
		display: none;
	}
	
	.recommend {
		height: 420rpx;
		overflow-x: auto;
		display: -webkit-box;
		-webkit-overflow-scrolling: touch;
	}
	
	.recommend .item {
		border: #eee 2px solid;
		text-align: center;
		width: 150rpx;
		padding: 50rpx;
		background-color: #fff;
		border-radius: 15rpx;
		margin-left: 20rpx;
		margin-top: 20rpx;
		box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.22);
	}
	
	.recommend .item .headimg image {
		width: 100rpx;
		border-radius: 50%;
	}
	
	.recommend .item .name {
		font-size: 22rpx;
		margin: 10rpx;
		width: 90%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space:nowrap;
	}
	
</style>
