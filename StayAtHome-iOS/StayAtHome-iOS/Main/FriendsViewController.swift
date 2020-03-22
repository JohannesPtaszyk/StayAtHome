
import UIKit

class FriendsViewController: UIViewController {

    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        title = "Freunde"
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}
