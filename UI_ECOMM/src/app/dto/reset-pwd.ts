export class ResetPwd {

    constructor(
        private email: string,
        private oldPwd: string,
        private newPwd: string,
        private conformPwd: string
    ) { }
}
