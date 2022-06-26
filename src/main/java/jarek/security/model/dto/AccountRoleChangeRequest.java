package jarek.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleChangeRequest {
    private Long accountId;
    private Map<Long, String> roles;
}
