
import UIKit

class ChallengesViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "Challenges"
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}
