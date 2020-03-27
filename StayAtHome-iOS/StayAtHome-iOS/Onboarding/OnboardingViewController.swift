import UIKit

enum OnboardingState {
    case done
    case hasErrors
    case none
}

protocol OnboardingDelegate: class {
    func stateChanged(_ viewController: UIViewController, to state: OnboardingState)
}

protocol KeyboardHandling {
    func keyboardActionTriggered()
    func keyboardStateChanged(_ state: KeyboardHandler.KeyboardDisplayType)
}

protocol OnboardingChild: class {
    var parentOnboardingViewController: OnboardingViewController? {get set}
    var state: OnboardingState {get set}
    var delegate: OnboardingDelegate? {get set}
}


class OnboardingViewController: UIViewController {

    let startViewController = UIStoryboard.onboarding.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.Onboarding.start)
    let userDataViewController = UIStoryboard.onboarding.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.Onboarding.userData)
    let locationPermissionViewController = UIStoryboard.onboarding.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.Onboarding.locationPermission)
    let acceptWIFIViewController = UIStoryboard.onboarding.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.Onboarding.acceptWIFI)
    
    var pageViewController: UIPageViewController?
    
    @IBOutlet weak var containerViewBottomConstraint: NSLayoutConstraint!
    var gameCoordinator: GameCoordinator?
    
    var viewControllers: [UIViewController] = []
    
    
    @IBOutlet weak var pageControl: UIPageControl!
    @IBOutlet weak var bottomConstraint: NSLayoutConstraint!
    
    lazy var keyboardHandler = KeyboardHandler(constraint: bottomConstraint, view: view)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupViewControllers()
        
        self.pageViewController?.dataSource = self
        self.pageViewController?.delegate = self
        self.pageViewController?.setViewControllers([viewControllers[0]], direction: .forward, animated: true, completion: nil)
        
        keyboardHandler.registerNotifications()
        
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .darkContent
    }
    
    func setupViewControllers() {
        
        viewControllers = [
            startViewController,
            userDataViewController,
            locationPermissionViewController,
            acceptWIFIViewController
        ]
        
        viewControllers.map {$0 as? OnboardingChild}.forEach { (child) in
            child?.delegate = self
            child?.parentOnboardingViewController = self
        }
        
        keyboardHandler.keyboardsHandlings = viewControllers.compactMap {$0 as? KeyboardHandling}
        
        pageControl.numberOfPages = viewControllers.count
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let pageViewController = segue.destination as? UIPageViewController {
            self.pageViewController = pageViewController
        }
    }

    func checkIfAllScreensAreDone() {
        let onboardingChildren = viewControllers.map {$0 as? OnboardingChild}
        
        if onboardingChildren.first(where: {$0?.state != .done}) == nil {
            finishOnboarding()
        }
    }
    
    func finishOnboarding() {
        var username: String = ""
        var email: String = ""
        var userImage: UIImage?
        var wifiName: String = ""
        
        if let userDataViewController = userDataViewController as? UserDataViewController {
            username = userDataViewController.userNameRoundTextField.text!
            email = userDataViewController.emailRoundTextField.text!
            userImage = userDataViewController.userImageView.image
        }
        
        if let acceptWifiViewController = acceptWIFIViewController as? AcceptWIFIViewController {
            wifiName = acceptWifiViewController.wifiName!
        }
        
        gameCoordinator?.presentGameFromOnboarding(withUserName: username, email: email, userImage: userImage, wifiName: wifiName)
    }
}



extension OnboardingViewController: UIPageViewControllerDataSource {
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = viewControllers.firstIndex(of: viewController) else {
            return nil
        }
        
        let previousIndex = viewControllerIndex - 1
        
        guard previousIndex >= 0 else {
            return nil
        }
        
        guard viewControllers.count > previousIndex else {
            return nil
        }
        
        pageControl.currentPage = previousIndex
        
        let previousViewController = viewControllers[previousIndex]
        
        previousViewController.view.endEditing(true)
        
        return previousViewController
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = viewControllers.firstIndex(of: viewController) else {
            return nil
        }
        
        let nextIndex = viewControllerIndex + 1
        let viewControllersCount = viewControllers.count
        
        guard viewControllersCount != nextIndex else {
            return nil
        }
        
        guard viewControllersCount > nextIndex else {
            return nil
        }
        
        pageControl.currentPage = nextIndex
        
        let nextViewController = viewControllers[nextIndex]
        
        nextViewController.view.endEditing(true)
        
        return nextViewController
    }
}

extension OnboardingViewController: OnboardingDelegate {
    func stateChanged(_ viewController: UIViewController, to state: OnboardingState) {
        checkIfAllScreensAreDone()
    }
    
}

extension OnboardingViewController: UIPageViewControllerDelegate {
    func pageViewController(_ pageViewController: UIPageViewController, willTransitionTo pendingViewControllers: [UIViewController]) {
        view.endEditing(true)
    }
}
