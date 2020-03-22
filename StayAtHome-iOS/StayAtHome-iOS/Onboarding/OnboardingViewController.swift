import UIKit

enum OnboardingState {
    case done
    case hasErrors
    case none
}

protocol OnboardingDelegate: class {
    func stateChanged(_ viewController: UIViewController, to state: OnboardingState)
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
    
    var gameCoordinator: GameCoordinator?
    
    var viewControllers: [UIViewController] = []
    
    @IBOutlet weak var doneButton: UIButton!
    @IBOutlet weak var pageControl: UIPageControl!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupViewControllers()
        
        self.pageViewController?.dataSource = self
        self.pageViewController?.setViewControllers([viewControllers[0]], direction: .forward, animated: true, completion: nil)
        
        pageControl.numberOfPages = viewControllers.count
        
        checkIfAllScreensAreDone()
            
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
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if let pageViewController = segue.destination as? UIPageViewController {
            self.pageViewController = pageViewController
        }
    }

    
    
    @IBAction func doneButtonTapped(_ sender: Any) {
        
        var username: String = ""
        var email: String = ""
        var userImage: UIImage?
        var wifiName: String = ""
        
        if let userDataViewController = userDataViewController as? UserDataViewController {
            username = userDataViewController.usernameTextField.text!
            email = userDataViewController.emailTextField.text!
            userImage = userDataViewController.userImageView.image
        }
        
        if let acceptWifiViewController = acceptWIFIViewController as? AcceptWIFIViewController {
            wifiName = acceptWifiViewController.wifiName!
        }
        
        gameCoordinator?.presentGameFromOnboarding(withUserName: username, email: email, userImage: userImage, wifiName: wifiName)
    }
    
    func checkIfAllScreensAreDone() {
        let onboardingChildren = viewControllers.map {$0 as? OnboardingChild}
        
        if let _ = onboardingChildren.first(where: {$0?.state != .done}) {
            doneButton.isHidden = true
        } else {
            doneButton.isHidden = false
        }
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
        
        return viewControllers[previousIndex]
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
        
        return viewControllers[nextIndex]
    }
}

extension OnboardingViewController: OnboardingDelegate {
    func stateChanged(_ viewController: UIViewController, to state: OnboardingState) {
        checkIfAllScreensAreDone()
    }
    
}
