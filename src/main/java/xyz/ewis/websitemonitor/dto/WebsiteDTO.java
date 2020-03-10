package xyz.ewis.websitemonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebsiteDTO
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebsiteDTO {
   Integer id;
   String url;
   String name;
}
