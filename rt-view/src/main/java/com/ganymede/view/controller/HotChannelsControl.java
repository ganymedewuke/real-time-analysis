package com.ganymede.view.controller;

import com.ganymede.view.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotCh")
public class HotChannelsControl {
	@Autowired
	private RedisService redisService;

	@RequestMapping("/listLastHotChannel")
	public String listLastHotChannel(Model model) {
		System.out.println("热点频道展示");
		String a = redisService.getStr("a");
		model.addAttribute("a", a);

		String channelId8 = redisService.getStr("channelId->8");
		model.addAttribute("channelId8", channelId8);
		return "hotChannels";
	}
}
