
import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var gameCoordinator: GameCoordinator!

    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        window = UIWindow(frame: UIScreen.main.bounds)
        
        gameCoordinator = GameCoordinator(managerProvider: ManagerProvider.shared)
        gameCoordinator.start(with: window!)
        
        return true
    }

}

