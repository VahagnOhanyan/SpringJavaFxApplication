package ohanyan.fw.auth;


import lombok.RequiredArgsConstructor;
import ohanyan.domain.UserInfoEntity;
import ohanyan.repo.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserAuthDetailsService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfoEntity> userInfo = userInfoRepository.findByUserLogin(username);
        if (userInfo.isPresent()) {
            UserInfoEntity user = userInfo.get();
            List<GrantedAuthority> grantedAuthorities = user.getUserRoleId().getPrivileges()
                    .stream()
                    .map(e -> new SimpleGrantedAuthority("role_" + e.getPrivilegeName() + ":" + e.getAccessTypeId().getAccessTypeId()))
                    .collect(Collectors.toList());

            grantedAuthorities.addAll(user.getPrivileges()
                    .stream()
                    .map(e -> new SimpleGrantedAuthority(e.getPrivilegeName() + ":" + e.getAccessTypeId().getAccessTypeId()))
                    .collect(Collectors.toList()));
            return new UserAuthDetails(user.getUserLogin(), user.getUserPass(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
