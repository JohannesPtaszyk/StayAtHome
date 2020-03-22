import UIKit

class GameCoordinator {
    
    var managerProvider: ManagerProvider
    
    var game = Game()
    var user: User?
    
    init(managerProvider: ManagerProvider) {
        self.managerProvider = managerProvider
    }
    
    func saveUser() {
        if let user = user {
            UserDataProvider.save(user: user)
        }
    }
    
    func start(with window: UIWindow) {
        
        UserDataProvider.fetchUser { [weak self] (result) in
            guard let strongSelf = self else {return}
            
            switch result {
            case .success(let user):
                strongSelf.user = user
                strongSelf.presentGame(in: window)
            case .failure(let error):
                strongSelf.handleUserFetchError(error, in: window)
                break
            }
        }
    }
    
    func presentGameFromOnboarding(withUserName userName: String, email: String, userImage: UIImage?, wifiName: String) {
        user = User(username: userName, email: email, score: 0)
        user?.wifiName = wifiName
        user?.image = userImage
        
        guard let user = user, let window = UIApplication.shared.keyWindow else {
            return
        }
        
        UserDataProvider.save(user: user)
        
        presentGame(in: window)
    }
    
    func presentGame(in window: UIWindow) {
        guard let mainViewController = UIStoryboard(name: Constants.Storyboards.main, bundle: nil).instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.MainViewController) as? MainViewController else {
            return
        }
        
        mainViewController.gameCoordinator = self
        
        window.rootViewController = mainViewController
        window.makeKeyAndVisible()
    }
    
    func handleUserFetchError(_ error: UserDataProvider.FetchUserError, in window: UIWindow) {
        switch error {
        case .noUserFound:
            displayOnboarding(in: window)
            break
        }
    }
    
    func displayOnboarding(in window: UIWindow) {
        guard let onboardingViewController = UIStoryboard(name: Constants.Storyboards.main, bundle: nil).instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.OnboardingViewController) as? OnboardingViewController else {
            return
        }
        
        onboardingViewController.gameCoordinator = self
        window.rootViewController = onboardingViewController
        window.makeKeyAndVisible()
    }
    
    func receiveSSID(_ completion:@escaping (_ ssid: String?, _ error: ReceiveSSIDError?) -> ()) {
        
        managerProvider.locationManager.askForLocationService { [weak self] authorized, location in
            guard let strongSelf = self else {
                completion(nil, nil)
                return
            }
            
            guard authorized else {
                completion(nil, .noLocationService)
                return
            }
            
            guard let wifiId = strongSelf.managerProvider.wifiManager.getWiFiSsid() else {
                completion(nil, .noWifi)
                return
            }
            
            completion(wifiId, nil)
        }
        
    }
    
}
