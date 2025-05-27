package com.examly.springappuser.config;

import com.examly.springappuser.config.UserPrinciple;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrinciple(user);
    }
    
}
