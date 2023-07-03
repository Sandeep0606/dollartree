# FD-mobile-app
FD-mobile-app repo

# Development tools:
IDE: VS Code

IDE extensions:
1.	ESLint v2.4.0
2.	Prettier – Code formatter v9.12.0
3.	Code Spell Checker v2.20.3

# APP RUN STEPS————

- **Project clean-up:**
delete the 'node-modules' folder from each package.
delete 'node-modules' & yarn.lock file from the root folder, i.e., 'family-dollar-mobile-app'
delete 'Pods', podfile.lock & 'build' folder from 'family_dollar_app/ios'
delete 'build' & 'app/build' folder from 'family_dollar_app/android'
 
- **Install dependencies:**
run the 'yarn' command from the root folder, i.e., 'family-dollar-mobile-app'
 
- **For pod install:**
goto 'packages/family_dollar_app/ios' folder and run 'bundle install && RCT_NEW_ARCH_ENABLED=1 bundle exec pod install' command
 
- **For Android build:**
make sure the metro bundler is running, else run the 'yarn metro' command from the root folder, i.e., 'family-dollar-mobile-app'
run the 'yarn android' command from the root folder, i.e., 'family-dollar-mobile-app'
 
- **For iOS build:**
make sure the metro bundler is running, else run the 'yarn metro' command from the root folder, i.e., 'family-dollar-mobile-app'
run the 'yarn ios' command from the root folder, i.e., 'family-dollar-mobile-app'
