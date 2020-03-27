import UIKit

enum SettingsMenuType: CaseIterable {
    case clearUserData
    
    var title: String {
        switch self {
        case .clearUserData:
            return "Clear User Data"
        }
    }
}

class SettingsViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    
    var gameCoordinator: GameCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.dataSource = self
        tableView.delegate = self
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}

extension SettingsViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        SettingsMenuType.allCases.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let settings = SettingsMenuType.allCases[indexPath.row]
        let cell = UITableViewCell()
        cell.backgroundColor = .clear
        cell.textLabel?.textColor = .white
        cell.textLabel?.text = settings.title
        
        return cell
    }
}

extension SettingsViewController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let selectedSetting = SettingsMenuType.allCases[indexPath.row]
        
        switch selectedSetting {
        case .clearUserData:
            gameCoordinator?.clearUserData()
        }
    }
    
}
