package cs.miu.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class StateProvince {

	@EqualsAndHashCode.Include
	private Long id;

	private String stateProvinceCode;

	private String name;

}
