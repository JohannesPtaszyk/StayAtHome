
import UIKit

class LeaderBoardViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "Bestenliste"
    }
    

    @IBAction func closeButtonTapped(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}
