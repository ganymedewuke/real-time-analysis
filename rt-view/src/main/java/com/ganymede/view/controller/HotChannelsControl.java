package com.ganymede.view.controller;

import com.ganymede.view.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/hotCh")
public class HotChannelsControl {
	@Autowired
	private RedisService redisService;

	@RequestMapping("/listLastHotChannel")
	public String listLastHotChannel(Model model, int topnum) {
		System.out.println("热点频道展示");

		Map<String, List<String>> map = redisService.getAllData("channelId*");
		Set<Map.Entry<String, List<String>>> set = map.entrySet();

		Map<Long, String> sortMap = new TreeMap<>(new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return Integer.parseInt((o1 - o2) + "");
			}
		});

		for (Map.Entry<String, List<String>> entry : set) {
			String channelId = entry.getKey();
			List<String> list = entry.getValue();

			long total = 0l;

			for (String s : list) {
				total += Long.parseLong(s);
			}

			if (sortMap.get(total) != null) {
				String channelIdTemp = sortMap.get(total);
				sortMap.put(total, channelIdTemp + "," + channelId);
			} else {
				sortMap.put(total, channelId);
			}
		}

		int temptotal = 0;
		Set<Map.Entry<Long, String>> sortMapset = sortMap.entrySet();
		List<String> result = new ArrayList<>();
		for (Map.Entry<Long, String> entry : sortMapset) {
			String channelId = entry.getValue();
			String[] temp = channelId.split(",");
			temptotal += temp.length;
			if (temptotal >= topnum) {
				//剩余的
				int sy = temptotal - topnum;
				//临时的取值
				int tempqz = temp.length - sy - 1;
				for (int i = 0; i <= tempqz; i++) {
					result.add(temp[i]);
				}
				break;
			} else {
				for (String tempinner : temp) {
					result.add(tempinner);
				}
			}
		}

		model.addAttribute("result", result);
		System.out.println(result);

		return "hotChannels";
	}
}
