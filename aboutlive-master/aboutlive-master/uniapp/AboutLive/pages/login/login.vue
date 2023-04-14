<template>
	<view class="content">

		<view class="title" style="color: #fff;">
			Sign In
		</view>

		<!--输入手机号-->
		<view class="input" style="border: 1px solid #fff;">
			<view class="font">
				User Name
			</view>
			<input type="text" placeholder="输入账号" v-model="UserName" />
		</view>

		<!--手机号错误提示区-->
		<!-- <view class="errorHint" style="display: none;">×请输入正确手机号</view> -->

		<!--输入密码-->
		<view class="input" style="border: 1px solid #fff;">
			<view class="font">
				User Password
			</view>
			<input type="password" placeholder="输入密码" v-model="PassWord" />
		</view>

		<!--忘记密码区域-->
		<view>
			<view class="forgetThePassword">
				忘记密码?
			</view>
			<view class="forgetThePassword">
				Forget The Password?
			</view>
		</view>

		<!--Sign In提交按钮-->
		<view class="button">
			<view @click="login()" class="input">Sign In</view>
		</view>
		<!--Sign In提交按钮-->
		<view class="button1">
			<view @click="fingerprint()">
				<image :src="zwimage[zwIndex]" mode="widthFix"></image>
			</view>
		</view>

		<view class="line" style="color:#fff;">———————OR———————</view>
		<!--分界线-->

<!-- 		<view class="vxAdmin">
			<view><image src="../../static/login/vx.png" mode="widthFix" alt=""></view>
			<view class="font">微信登录</view>
		</view> -->

		<view class="callAdmin" @click="register()">
			注册
		</view>
		<view class="vx" @click="vx()" style="display: flex;justify-content: center;">
			微信登录
			<view style="margin-top: 10rpx;margin-left: 20rpx;">
				<image src="../../static/login/vx.png" mode="widthFix"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				UserName: "", //用户名
				PassWord: "", //密码
				zwIndex:0,
				zwimage:["../../static/login/zwTrue.png","../../static/login/zwTrue.png","../../static/login/zw.png"]
			}
		},
		onLoad() {
			console.log("test")
			var _this=this;
			uni.getStorage({
				key:'userArrays',
				success:function(res){
					getApp().globalData.userArrays=JSON.parse(res.data);
				}
			})
			uni.getStorage({
				key:'userKey',
				success:function(res){
					getApp().globalData.userKey=JSON.parse(res.data);
				}
			})
			if(getApp().globalData.userKey!=null && getApp().globalData.userKey!=""){
				uni.switchTab({
					url:'../index/index'
				})
			}
			// onLoad() {
			//     var userArrays=wx.getStorageSync('userArrays')
			//       var userKey=wx.getStorageSync('userKey')
			//       console.log("userKey: "+userKey)
			//       if(userKey!='' && userKey!=null){
			//         console.log(userKey)
			//         getApp().globalData.userKey = userKey;
			//         getApp().globalData.userArrays = userArrays;
			//         wx.switchTab({
			//           url: '../index/index',
			//         })
			//       }
			//     console.log("test");
			//   },
		},
		methods: {
			login() {
				uni.showLoading({
					title: '加载中...'
				});
				if (this.UserName !== "" && this.PassWord !== "") {
					uni.request({
						data: {
							UName: this.UserName,
							UPwd: this.PassWord
						},
						method: "GET",
						url: getApp().globalData.BaseUrl+"/User/login",
						success: function(res) {
							if (res.data.code == 0) {
								console.log(res.data.msg)
								if(res.data.msg[8]==1){
									getApp().globalData.userKey = res.data.msg[0]
									getApp().globalData.userArrays = res.data.msg
									uni.setStorage({key:"userArrays",data:JSON.stringify(res.data.msg)})
									uni.setStorage({key:"userKey",data:JSON.stringify(res.data.msg[0])})
									
									uni.switchTab({ //tab返回专用
										url: "../index/index"
									})
								}else{
									uni.showToast({
										title: '已封号',
										icon:'error',
										duration: 2000
									});
								}
							} else {
								uni.showModal({
									content: "用户名或密码错误！"
								})
							}

						}
					})

				} else {
					uni.showModal({
						content: "用户名或密码为空，请检查！"
					})
				}
				uni.hideLoading();
			},
			
			register(){
				uni.navigateTo({
					url:"../register/register"
				})
			},
			
			
			fingerprint(){
				var that = this;
				uni.checkIsSupportSoterAuthentication({
					success(res) {
						// console.log(res);
						// 如果当前设备支持生物识别方式，且支持指纹识别方式
						if (res.supportMode && res.supportMode.includes('fingerPrint')) {
							/** 
							 * uni.checkIsSoterEnrolledInDevice : 获取设备内是否录入指纹信息
							 *  checkAuthMode: 'fingerPrint', // 检验指纹信息
							 * */
							uni.checkIsSoterEnrolledInDevice({
								checkAuthMode: 'fingerPrint', // 检验指纹信息
								success(res) {
									// console.log(res.isEnrolled)
									if (res.isEnrolled == true) {
										/** 
										 * 开始 SOTER 生物认证
										 * 执行成功，进行后续操作
										 * */
										uni.startSoterAuthentication({
											requestAuthModes: ['fingerPrint'],
											challenge: '123456',
											authContent: '请用指纹解锁',
											success(res) {
												// console.log(res);
												that.disabled=false;
												that.zwIndex=1;
												that.reault="验证成功"
												//指纹识别成功后，进行后续工作
												that.PhoneMac();
											},
											fail(err) {
												uni.showToast({
													title: '验证失败！',
													icon:'error',
													duration: 2000
												});
											}
										})
									} else {
										uni.showToast({
											title: '此设备未录入指纹，请到设置中开启',
											icon: 'none',
											duration: 2000
										});
										
										that.zwIndex=2;
										uni.showToast({
											title: '验证失败！',
											icon:'error',
											duration: 2000
										});
									}
								},
								fail(err) {
									uni.showToast({
										title: '此设备未录入指纹，请到设置中开启',
										icon: 'none',
										duration: 2000
									});
									that.zwIndex=2;
								}
							})
				
						} else {
							uni.showToast({
								title: '此设备不支持指纹识别功能',
								icon: 'none',
								duration: 2000
							});
							that.zwIndex=2;
						}
					},
					fail(err) {
						uni.showToast({
							title: '此设备不支持指纹识别功能',
							icon: 'none',
							duration: 2000
						});
						that.disabled=false;
						that.zwIndex=2;
						that.reault="验证失败"
					}
				})
			},
			
			// 获取手机mac
			PhoneMac(){
				var that=this;
				// #ifdef APP
				// 导入Java类。Networklnterface类表示一个由名称和分配给此接口的IP地址列表组成的网络接口
				const net = plus.android.importClass('java.net.NetworkInterface');
				// 搜索具有指定名称的网络接口
				const wlan0 = net.getByName('wlan0');
				// 获得网卡的硬件地址
				const macByte = wlan0.getHardwareAddress();
				let mac = '';
				//转换MAC地址的思路来自网上(https://blog.csdn.net/zhangzhen53377562/article/details/109183891)
				macByte.forEach(item => {
					// .toString(16)数字以十六进制值显示
					let temp = '';
					if (item < 0) temp = (256 + item).toString(16);
					else temp = item.toString(16);
					if (temp.length == 1) temp = `0${temp}`;
					mac += temp;
				});
				mac = mac.toUpperCase();
				let mac2 = mac;
				for (let i = 2; i < mac2.length; i += 3) mac2 = mac2.slice(0, i) + ':' + mac2.slice(i);
				// console.log('MAC: ' + mac);
				// console.log('MAC2: ' + mac2);
				// console.log('IMEI: ' + plus.device.imei);
				plus.device.getInfo({
					success: function(e) {
						// console.log('getDeviceInfo success: ' + JSON.stringify(e));
						console.log("uuid:"+e.uuid);
						uni.request({
							data: {
								fingerprintLogin: e.uuid
							},
							method: "GET",
							url: getApp().globalData.BaseUrl+"/User/fingerprintLogin",
							success: function(res) {
								console.log("res:"+res);
								if(res.data.code==0){
									if(res.data.data.state==1){
										getApp().globalData.userKey = res.data.data
										getApp().globalData.userArrays = res.data.data.userKey
										uni.setStorage({key:"userArrays",data:JSON.stringify(res.data.data)})
										uni.setStorage({key:"userKey",data:JSON.stringify(res.data.data.userKey)})
										
										uni.switchTab({ //tab返回专用
											url: "../index/index"
										})
									}else{
										uni.showToast({
											title: '已封号',
											icon:'error',
											duration: 2000
										});
									}
								}else{
									uni.showToast({
										title: '"验证成功，未查到该用户，如若换过手机请邮箱登录后重新验证指纹"',
										icon:'error',
										duration: 2000
									});
									
								}
							}
						})
					}
				});
				// #endif
			
			},
			
			vx(){
				uni.getProvider({
					service: 'oauth',
					success: function(res) {
						console.log(res.provider);
						// 手机app支持微信、qq和微博等
						if (~res.provider.indexOf('weixin')) {
							console.log('weixin');
							uni.login({
								provider: 'weixin',
								success: function(loginRes) {
									console.log(JSON.stringify(loginRes));
									// 获取用户信息
									uni.getUserInfo({
										provider: 'weixin',
										success: function(infoRes) {
											console.log('-------获取微信用户所有-----');
											console.log(JSON.stringify(infoRes
												.userInfo));
											const userInfo = infoRes.userInfo
											getApp().globalData.userInfo = userInfo;
											getApp().globalData.wxCode = userInfo
												.nickName;
											console.log(getApp().globalData.userInfo);
											uni.switchTab({
												url: '.../index/index'
											})
										}
									});
								},
								fail: function(res) {
									console.log(res)
									uni.showToast({
										icon: 'none',
										title: '登陆失败,请确保已安装或已登录微信',
										//title: res.errMsg,
										duration: 2000
									});
									setTimeout(function() {
										uni.switchTab({
											url: '.../index/index'
										})
									}, 2000)
								}
							});
						}
					}
				});
			}


			// call(e) { //输入电话号码时，判断号码是否超过十一位

			// },
			// codeVerification(phone) { //正则表达式判断
			// 	let phoneCodeVerification = /^[1][0-9][0-9]{9}$/;
			// 	return phoneCodeVerification.test(phone);
			// }
		}
	}
</script>

<style>
	page {
		background-color: #212121;
		font-size: 26rpx;
	}

	.title {
		font-size: 8vw;
		text-align: center;
		padding-top: 25vw;
	}

	.input {
		width: 80%;
		height: 10vw;
		border-radius: 20vw;
		margin: 2vw auto;
		margin-top: 10vw;
		transition: 1s;
	}

	.input .font {
		background-color: #212121;
		width: 30vw;
		color: #fff;
		font-size: 3vw;
		margin-top: -3vw;
		margin-left: 6vw;
		text-align: center;
	}

	.input input {
		background-color: #212121;
		border: none;
		margin-top: 2vw;
		margin-left: 5vw;
		font-size: 4vw;
		color: #fff;
		width: 85%;
		outline: none;
	}

	/*手机号错误提示*/
	.errorHint {
		color: red;
		font-size: 3vw;
		text-align: right;
		margin-right: 12vw;
		transition: 0.2s;
	}


	.forgetThePassword {
		/*忘记密码*/
		color: #3F51B5;
		position: relative;
		text-align: right;
		right: 15vw;
		font-size: 3vw;
	}

	/*按钮样式*/
	.button {
		text-align: center;
		margin-top: 50rpx;
	}

	.button .input {
		background-color: #3F51B5;
		border: none;
		padding: 10rpx 0rpx;
		width: 80%;
		height: 80rpx;
		margin: 0 auto;
		border-radius: 20rpx;
		color: #fff;
		font-size: 28rpx;
		line-height: 80rpx;
	}

	/*分界线样式*/
	.line {
		text-align: center;
		margin-top: 5vw;
		transition: 1s;
	}

	.vxAdmin {
		background-color: #04BE02;
		width: 80%;
		margin: 0 auto;
		text-align: center;
		height: 12vw;
		margin-top: 5vw;
		display: flex;
		justify-content: center;
		border-radius: 2vw;
	}

	.vxAdmin image {
		width: 8vw;
		height: 8vw;
		margin-top: 2vw;
	}

	.vxAdmin .font {
		color: #fff;
		margin-top: 3.5vw;
		margin-left: 5vw;
		font-size: 4vw;
	}

	.callAdmin {
		background-color: #3F51B5;
		border: none;
		margin: 4vw auto;
		width: 80%;
		height: 12vw;
		border-radius: 2vw;
		color: #fff;
		text-align: center;
		line-height: 12vw;
		font-size: 4vw;
	}
	.vx {
		background-color: #34b53c;
		border: none;
		margin: 4vw auto;
		width: 80%;
		height: 12vw;
		border-radius: 2vw;
		color: #fff;
		text-align: center;
		line-height: 12vw;
		font-size: 4vw;
	}
	.vx image{
		width: 50rpx;
		margin-top: 10rpx;
	}
	.button1{
		background-color: #e8e8e8;
		width: 100rpx;
		height: 100rpx;
		padding: 10rpx;
		text-align: center;
		border-radius: 50%;
		margin: 0 auto;
		margin-top: 50rpx;
	}
	.button1 image{
		width: 80rpx;
		margin-top: 10rpx;
	}
</style>
