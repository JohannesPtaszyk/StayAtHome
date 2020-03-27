
import UIKit

class StartViewController: UIViewController, OnboardingChild {
    
    var state: OnboardingState = .done
    var delegate: OnboardingDelegate?
    var parentOnboardingViewController: OnboardingViewController?

    override func viewDidLoad() {
        super.viewDidLoad()

    }
    


}
